package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EHitStatus;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class FlameAttack extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  private DamageInflicted damageInflicted;

  public FlameAttack(PlayerCharacter caster)
  {
    super("Flame Attack", caster);
  }

  @Override
  public boolean cast()
  {
    Creature target = getCaster().getParty().getActiveEncounter().getEnemy();
    DiceRollCmd roll = new DiceRollCmd(getCaster().getStats().getLevel(), 8);
    CommandDelegate.execute(roll);
    damageInflicted = generateDamage(getCaster(), roll.getTotal(), target);

    DealDamageCmd damageCmd = new DealDamageCmd(damageInflicted, target);
    CommandDelegate.execute(damageCmd);
    return true;
  }

  private DamageInflicted generateDamage(Fighter attacker, int totalDamage, Fighter target)
  {
    Weapon weapon = new Weapon();
    weapon.name = getSpellName();
    weapon.customIcon = "✨";
    return new DamageInflicted(attacker, weapon, EHitStatus.HIT, totalDamage, target, 0);
  }

  @Override
  public void expire()
  {
    // nothing to do
  }

  @Override
  public String buildSpellDescription()
  {
    if (!isCast())
    {
      return super.buildSpellDescription();
    }
    return damageInflicted.buildDamageDescription();
  }

  @Override
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }
}
