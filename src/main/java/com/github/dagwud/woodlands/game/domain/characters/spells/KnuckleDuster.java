package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class KnuckleDuster extends SingleCastSpell
{
  private static final int DAMAGE_MULTIPLIER = 2;

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
    getCaster().getStats().setDamageMultiplier(Math.floorDiv(getCaster().getStats().getDamageMultiplier(), DAMAGE_MULTIPLIER));
  }
}
