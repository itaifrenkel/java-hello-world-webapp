package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class KnuckleDuster extends SingleCastSpell
{
  private static final double DAMAGE_MULTIPLIER = 2;

  public KnuckleDuster(PlayerCharacter caster)
  {
    super("Knuckle Duster", caster);
  }

  @Override
  public void cast()
  {
    getCaster().getStats().setDamageMultiplier(getCaster().getStats().getDamageMultiplier() * DAMAGE_MULTIPLIER);
  }

  @Override
  public void expire()
  {
    getCaster().getStats().setDamageMultiplier(Math.floor(getCaster().getStats().getDamageMultiplier() / DAMAGE_MULTIPLIER));
  }

  @Override
  public int getManaCost()
  {
    return 0;
  }
}
