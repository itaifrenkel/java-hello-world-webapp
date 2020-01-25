package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.GameCharacter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpiritOfAdventure extends Spell
{
  private static final BigDecimal CHANCE_OF_ENCOUNTER_MODIFIER = new BigDecimal("1.5");

  public SpiritOfAdventure(GameCharacter caster)
  {
    super("Spirit of Adventure", caster);
  }

  @Override
  public boolean shouldCast()
  {
    return false;
  }

  @Override
  public void cast()
  {
    BigDecimal initial = getCaster().getParty().getPercentChanceOfEncounter();
    getCaster().getParty().setPercentChanceOfEncounter(initial.multiply(CHANCE_OF_ENCOUNTER_MODIFIER));
  }

  @Override
  public void expire()
  {
    BigDecimal initial = getCaster().getParty().getPercentChanceOfEncounter();
    getCaster().getParty().setPercentChanceOfEncounter(initial.divide(CHANCE_OF_ENCOUNTER_MODIFIER, RoundingMode.HALF_UP));
  }
}
