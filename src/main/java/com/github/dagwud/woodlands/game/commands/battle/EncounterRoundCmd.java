package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.EncounterStatus;
import com.github.dagwud.woodlands.game.domain.ManualEncounter;

public abstract class EncounterRoundCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerState playerState;
  private final int delayBetweenRoundsMS;

  EncounterRoundCmd(PlayerState playerState, int delayBetweenRoundsMS)
  {
    super(new EncounterNotEndedPrerequisite(playerState.getActiveCharacter().getParty().getActiveEncounter()));
    this.playerState = playerState;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    Encounter encounter = playerState.getActiveCharacter().getParty().getActiveEncounter();
    if (encounter.isEnded())
    {
      return;
    }
    encounter.setHasAnyPlayerActivityPrepared(false);
    encounter.setStatus(EncounterStatus.PREPARE_ACTIONS);
    executePreparationPhase(encounter);
    scheduleFightPhase(encounter, playerState);
  }

  abstract void executePreparationPhase(Encounter encounter);

  protected abstract void scheduleFightPhase(Encounter encounter, PlayerState playerState);

  int getDelayBetweenRoundsMS()
  {
    return delayBetweenRoundsMS;
  }

  @Override
  public String toString()
  {
    return "EncounterRoundCmd{" +
            "playerState=" + playerState.getActiveCharacter().getName() +
            '}';
  }
}
