package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.battle.EHitStatus;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class SneakAttack extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  public SneakAttack(PlayerCharacter caster)
  {
    super("Sneak Attack", caster);
  }

  @Override
  public boolean cast()
  {
    Creature target = getCaster().getParty().getActiveEncounter().getEnemy();
    DiceRollCmd roll = new DiceRollCmd(Math.floorDiv(getCaster().getStats().getLevel() - 1, 2) + 1, 8);
    CommandDelegate.execute(roll);
    setDamageInflicted(generateDamage(getCaster(), roll.getTotal(), target));

    DealDamageCmd damageCmd = new DealDamageCmd(getDamageInflicted(), target);
    CommandDelegate.execute(damageCmd);
    return true;
  }

  private DamageInflicted generateDamage(Fighter attacker, int totalDamage, Fighter target)
  {
    Weapon weapon = new Weapon(getSpellName());
    weapon.customIcon = Icons.MANA;
    return new DamageInflicted(attacker, weapon, EHitStatus.HIT, totalDamage, target, 0);
  }

  @Override
  public void expire()
  {
    // nothing to do
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
