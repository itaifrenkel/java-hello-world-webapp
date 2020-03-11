package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.List;

public abstract class GenerateAutomaticEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  public GenerateAutomaticEncounterCmd(PlayerState playerState, ELocation location, int enemyCount, double minDifficulty, double maxDifficulty, String creatureType)
  {
    super(playerState, location, enemyCount, minDifficulty, maxDifficulty, creatureType);
  }

  @Override
  protected boolean shouldHaveEncounter()
  {
    ChanceCalculatorCmd chance = new ChanceCalculatorCmd(getPlayerState().getActiveCharacter().getParty().getPercentChanceOfEncounter());
    CommandDelegate.execute(chance);
    return chance.getResult();
  }

  @Override
  Encounter createEncounter(FightingGroup aggressor, FightingGroup enemy)
  {
    return new Encounter(aggressor, enemy);
  }

  @Override
  protected void scheduleFirstRound(Encounter encounter)
  {
    EncounterRoundCmd cmd = new AutomaticEncounterRoundCmd(getPlayerState(), Settings.DELAY_BETWEEN_ROUNDS_MS);
    CommandDelegate.execute(cmd);
  }
}
