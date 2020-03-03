package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.List;

public class BuildRoundSummaryCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Encounter encounter;
  private final List<DamageInflicted> damageInflicted;
  private final List<? extends Spell>[] spellGroups;
  private String summary;

  BuildRoundSummaryCmd(Encounter encounter, List<DamageInflicted> damageInflicted, List<? extends Spell>... spellGroups)
  {
    this.encounter = encounter;
    this.damageInflicted = damageInflicted;
    this.spellGroups = spellGroups;
  }

  @Override
  public void execute()
  {
    if (noSpells(spellGroups) && damageInflicted.isEmpty())
    {
      summary = "";
      return;
    }
    StringBuilder b = new StringBuilder();
    b.append("⚔️ <u>Battle Round #").append(encounter.getBattleRound()).append(":</u> ⚔️\n");

    for (List<? extends Spell> spells : spellGroups)
    {
      for (Spell spell : spells)
      {
        b.append("\n").append(bullet(spell.getCaster()))
                .append(spell.getCaster().getName()).append(" ")
                .append(spell.buildSpellDescription());
      }
    }

    for (DamageInflicted damage : damageInflicted)
    {
      b.append("\n").append(bullet(damage.getAttacker()))
              .append(damage.buildDamageDescription());
    }
    b.append("\n\n").append(buildBattleStatsSummary());
    summary = b.toString();
  }

  private boolean noSpells(List<? extends Spell>... spellGroups)
  {
    for (List<? extends Spell> spells : spellGroups)
    {
      if (!spells.isEmpty())
      {
        return false;
      }
    }
    return true;
  }

  private String buildBattleStatsSummary()
  {
    StringBuilder b = new StringBuilder();
    b.append("<u>Stats after round ").append(encounter.getBattleRound()).append("</u>\n");
    for (GameCharacter member : encounter.getParty().getActiveMembers())
    {
      b.append("\n").append(bullet(member)).append(member.summary());
    }
    for (Creature enemy : encounter.getEnemies())
    {
      b.append("\n").append(bullet(enemy)).append(enemy.summary());
    }
    return b.toString();
  }

  private String bullet(Fighter fighter)
  {
    return (fighter instanceof Creature) ? "🔸" : "🔹";
  }

  public String getSummary()
  {
    return summary;
  }
}
