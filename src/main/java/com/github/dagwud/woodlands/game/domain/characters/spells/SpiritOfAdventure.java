package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpiritOfAdventure extends PartySpell
{
  private static final BigDecimal CHANCE_OF_ENCOUNTER_MODIFIER = new BigDecimal("1.5");

  public SpiritOfAdventure(PlayerCharacter caster)
  {
    super("Spirit of Adventure", caster);
  }

  @Override
  public boolean cast()
  {
    BigDecimal initial = getCaster().getParty().getPercentChanceOfEncounter();
    getCaster().getParty().setPercentChanceOfEncounter(initial.multiply(CHANCE_OF_ENCOUNTER_MODIFIER));
    if (!getCaster().getParty().isPrivateParty())
    {
      SendPartyMessageCmd cmd = new SendPartyMessageCmd(getCaster().getParty(), getCaster().getName() + " is boosting the chance of encounters for " + getCaster().getParty().getName());
      CommandDelegate.execute(cmd);
    }
    return true;
  }

  @Override
  public void expire()
  {
    BigDecimal initial = getCaster().getParty().getPercentChanceOfEncounter();
    getCaster().getParty().setPercentChanceOfEncounter(initial.divide(CHANCE_OF_ENCOUNTER_MODIFIER, RoundingMode.HALF_UP));
    if (!getCaster().getParty().isPrivateParty())
    {
      SendPartyMessageCmd cmd = new SendPartyMessageCmd(getCaster().getParty(), getCaster().getName() + " is no longer boosting the chance of encounters for " + getCaster().getParty().getName());
      CommandDelegate.execute(cmd);
    }
  }
}
