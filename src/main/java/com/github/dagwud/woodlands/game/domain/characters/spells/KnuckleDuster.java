package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.gson.game.Weapon;
import com.github.dagwud.woodlands.game.commands.battle.AttackCmd;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;

public class KnuckleDuster extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;
  private static final double DAMAGE_MULTIPLIER = 2;

  public KnuckleDuster(PlayerCharacter caster)
  {
    super("Spiritual Knuckle Duster", caster);
  }

  @Override
  public boolean cast()
  {
    int hitChanceBoost = 10_000;
    getCaster().getStats().setDamageMultiplier(getCaster().getStats().getDamageMultiplier() * DAMAGE_MULTIPLIER);
    getCaster().getStats().setHitBoost(getCaster().getStats().getHitBoost() + hitChanceBoost);
    DamageInflicted damage = doAttack();
    setDamageInflicted(damage);
    getCaster().getStats().setHitBoost(getCaster().getStats().getHitBoost() - hitChanceBoost);
    getCaster().getStats().setDamageMultiplier(Math.floor(getCaster().getStats().getDamageMultiplier() / DAMAGE_MULTIPLIER));
    getCaster().getStats().setDamageMultiplier(1d);
    return true;
  }

  public DamageInflicted doAttack()
  {
    Fighter enemy = getCaster().getParty().getActiveEncounter().chooseFighterToAttack(getCaster());
    Weapon weapon = (Weapon)(getCaster().getCarrying().getCarriedLeft());
    AttackCmd attack = new AttackCmd(getCaster(), weapon, enemy);
    CommandDelegate.execute(attack);
    
    DealDamageCmd dealDamage = new DealDamageCmd(attack.getDamageInflicted(), enemy);
    CommandDelegate.execute(dealDamage);
    
    return attack.getDamageInflicted();
  }

  @Override
  public void expire()
  {
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }

  @Override
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter)super.getCaster();
  }
}
