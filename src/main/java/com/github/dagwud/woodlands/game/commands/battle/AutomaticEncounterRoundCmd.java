package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.Encounter;

public class AutomaticEncounterRoundCmd extends EncounterRoundCmd
{
  private static final long serialVersionUID = 1L;

  public AutomaticEncounterRoundCmd(PlayerState playerState, int delayBetweenRoundsMs)
  {
    super(playerState, delayBetweenRoundsMs);
  }

  @Override
  void executePreparationPhase(Encounter encounter)
  {
    // nothing to do
  }

  @Override
  protected void scheduleFightPhase(Encounter encounter, PlayerState playerState)
  {
    // no schedule as such; do it immediately
    EncounterRoundFightCmd fight = new EncounterRoundFightCmd(playerState, encounter, getDelayBetweenRoundsMS());
    CommandDelegate.execute(fight);
  }
}
