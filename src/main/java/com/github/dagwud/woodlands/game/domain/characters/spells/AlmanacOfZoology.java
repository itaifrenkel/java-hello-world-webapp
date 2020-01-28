package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AlmanacOfZoology extends SingleCastSpell
{
  private static final double DAMAGE_MULTIPLIER = 1.4;

  public AlmanacOfZoology(PlayerCharacter caster)
  {
    super("Almanac of Zoology", caster);
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
