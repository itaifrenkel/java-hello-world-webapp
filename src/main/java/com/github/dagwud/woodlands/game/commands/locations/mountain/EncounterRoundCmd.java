package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.battle.OrderFightersCmd;
import com.github.dagwud.woodlands.game.commands.character.CastSpellCmd;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.*;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.BattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.*;

public class EncounterRoundCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final Encounter encounter;
  private final int delayBetweenRoundsMS;

  EncounterRoundCmd(int chatId, Encounter encounter, int delayBetweenRoundsMS)
  {
    super(new EncounterNotEndedPrerequisite(encounter));
    this.chatId = chatId;
    this.encounter = encounter;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    // Note that we need to keep re-checking order of fight since the fighters involved may change (e.g. due
    // to spells like Army of Peasants)
    List<Fighter> passivesOrder = buildOrderOfFight(encounter.getAllFighters());
    List<BattleRoundSpell> passivesActivity = doPassiveAbilities(passivesOrder);

    List<Fighter> spellOrder = buildOrderOfFight(encounter.getAllFighters());
    List<SingleCastSpell> spellsActivity = doPreparedSpells(spellOrder);

    List<Fighter> fightOrder = buildOrderOfFight(encounter.getAllFighters());
    List<DamageInflicted> roundActivity = doFighting(fightOrder);

    expireSpells(spellsActivity);
    expirePassiveAbilities(passivesActivity);

    String summary = buildRoundSummary(roundActivity, passivesActivity, spellsActivity);
    SendPartyMessageCmd status = new SendPartyMessageCmd(encounter.getParty(), summary);
    CommandDelegate.execute(status);

    PlayerCharacter inDanger = getAnyPlayerInDanger();
    if (inDanger != null)
    {
      if (encounter.getParty().capableOfRetreat())
      {
        RetreatCmd retreat = new RetreatCmd(inDanger);
        CommandDelegate.execute(retreat);
      }
      else
      {
        TooManyUnconsciousCmd killall = new TooManyUnconsciousCmd(encounter.getParty());
        CommandDelegate.execute(killall);
      }

      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);

      return;
    }

    if (!encounter.getEnemy().isConscious() || !anyPlayerCharactersStillAlive(encounter))
    {
      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);
    }

    if (!encounter.isEnded())
    {
      scheduleNextRound();
    }
    else
    {
      if (!encounter.getParty().canAct())
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), "You have been defeated!");
        CommandDelegate.execute(cmd);
      }
      else if (!encounter.getEnemy().isConscious())
      {
        DefeatCreatureCmd win = new DefeatCreatureCmd(encounter.getParty(), encounter.getEnemy());
        CommandDelegate.execute(win);

        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), encounter.getEnemy().name + " has been defeated! Each player gains " + win.getExperienceGrantedPerPlayer() + " experience");
        CommandDelegate.execute(cmd);
      }
    }
  }

  private List<Fighter> buildOrderOfFight(Collection<Fighter> fighters)
  {
    OrderFightersCmd cmd = new OrderFightersCmd(fighters);
    CommandDelegate.execute(cmd);
    return cmd.getOrderedFighters();
  }

  private void expireSpells(List<SingleCastSpell> spellsActivity)
  {
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(spellsActivity);
    CommandDelegate.execute(expireAll);
  }

  private List<SingleCastSpell> doPreparedSpells(List<Fighter> fighters)
  {
    List<SingleCastSpell> spellsCast = new ArrayList<>(2);
    for (Fighter caster : fighters)
    {
      while (caster.getSpellAbilities().hasPreparedSpell())
      {
        SingleCastSpell spell = caster.getSpellAbilities().popPrepared();
        CastSpellCmd cast = new CastSpellCmd(spell);
        CommandDelegate.execute(cast);
        spellsCast.add(spell);
      }
    }
    return spellsCast;
  }

  private void expirePassiveAbilities(List<BattleRoundSpell> spellsActivity)
  {
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(spellsActivity);
    CommandDelegate.execute(expireAll);
  }

  private List<BattleRoundSpell> doPassiveAbilities(List<Fighter> fighters)
  {
    List<BattleRoundSpell> passives = new ArrayList<>();
    for (Fighter member : fighters)
    {
      passives.addAll(member.getSpellAbilities().getPassives());
    }

    passives.removeIf(p -> !p.shouldCast());

    for (BattleRoundSpell spellToCast : passives)
    {
      CommandDelegate.execute(new CastSpellCmd(spellToCast));
    }

    if (!passives.isEmpty())
    {
      WaitCmd wait = new WaitCmd(1000);
      CommandDelegate.execute(wait);
    }

    return passives;
  }

  private List<DamageInflicted> doFighting(List<Fighter> fighters)
  {
    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    for (Fighter fighter : fighters)
    {
      doAttack(fighter, roundActivity);
    }
    return roundActivity;
  }

  private PlayerCharacter getAnyPlayerInDanger()
  {
    for (PlayerCharacter member : encounter.getParty().getActivePlayerCharacters())
    {
      double hpPerc = ((double) member.getStats().getHitPoints()) / ((double) member.getStats().getMaxHitPoints());
      if (hpPerc <= 0.2)
      {
        return member;
      }
    }
    return null;
  }

  private boolean anyPlayerCharactersStillAlive(Encounter encounter)
  {
    for (PlayerCharacter member : encounter.getParty().getActivePlayerCharacters())
    {
      if (member.isConscious())
      {
        return true;
      }
    }
    return false;
  }

  private void doAttack(Fighter attacker, List<DamageInflicted> roundActivity)
  {
    boolean didAnything = false;
    Fighter defender = attacker.chooseFighterToAttack(encounter.getAllFighters());
    if (attacker.isConscious() && defender.isConscious())
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedLeft(), defender);
      roundActivity.add(damage);
      didAnything = true;
    }
    if (attacker.isConscious() && defender.isConscious())
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedRight(), defender);
      roundActivity.add(damage);
      didAnything = true;
    }

    if (!didAnything)
    {
      // add a "did nothing" entry
      DamageInflicted nothing = doAttack(attacker, null, defender);
      roundActivity.add(nothing);
    }
  }

  private DamageInflicted doAttack(Fighter attacker, Weapon attackWith, Fighter defender)
  {
    AttackCmd attack = new AttackCmd(attacker, attackWith, defender);
    CommandDelegate.execute(attack);
    DamageInflicted damageInflicted = attack.getDamageInflicted();

    DealDamageCmd cmd = new DealDamageCmd(damageInflicted, defender);
    CommandDelegate.execute(cmd);
    return damageInflicted;
  }

  private String buildRoundSummary(List<DamageInflicted> damage, List<? extends Spell>... spellGroups)
  {
    StringBuilder summary = new StringBuilder();
    summary.append("⚔️ Battle Round #").append(encounter.getBattleRound()).append(": ⚔️\n")
            .append("———————————");

    for (List<? extends Spell> spells : spellGroups)
    {
      for (Spell spell : spells)
      {
        summary.append("\n").append(spell.getCaster().getName()).append(" ").append(spell.buildSpellDescription());
      }
    }

    for (DamageInflicted damageInflicted : damage)
    {
      summary.append("\n").append(damageInflicted.buildDamageDescription());
    }
    summary.append("\n\n").append(buildBattleStatsSummary());
    return summary.toString();
  }

  private String buildBattleStatsSummary()
  {
    StringBuilder b = new StringBuilder();
    b.append("Stats after round ").append(encounter.getBattleRound()).append("\n")
            .append("—————————");
    for (GameCharacter member : encounter.getParty().getActiveMembers())
    {
      b.append("\n").append("• ").append(member.summary());
    }
    b.append("\n").append("• ").append(encounter.getEnemy().summary());
    return b.toString();
  }

  private void scheduleNextRound()
  {
    EncounterRoundCmd nextRoundCmd = new EncounterRoundCmd(chatId, encounter, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);

    SendPartyMessageCmd msg = new SendPartyMessageCmd(encounter.getParty(), "Next round of battle in " + (delayBetweenRoundsMS / 1000) + " seconds");
    CommandDelegate.execute(msg);
  }

  @Override
  public String toString()
  {
    return "EncounterRoundCmd{" +
            "chatId=" + chatId +
            ", encounter=" + encounter +
            '}';
  }
}
