package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.PlayerState;

public abstract class SuspendableCmd extends AbstractCmd
{
  private final PlayerState playerState;
  private final int numberOfPhases;
  private int nextPhaseToRun;
  private String capturedInput;

  protected SuspendableCmd(PlayerState playerState, int numberOfPhases)
  {
    this.playerState = playerState;
    this.numberOfPhases = numberOfPhases;
  }

  @Override
  public final void execute()
  {
    executePart(nextPhaseToRun, capturedInput);
    if (nextPhaseToRun == 0)
    {
      playerState.setWaitingForInputCmd(this);
    }
    nextPhaseToRun++;
    capturedInput = null;
    if (nextPhaseToRun == numberOfPhases)
    {
      playerState.setWaitingForInputCmd(null);
    }
  }

  protected abstract void executePart(int phaseToExecute, String capturedInput);

  void setCapturedInput(String input)
  {
    this.capturedInput = input;
  }

  protected PlayerState getPlayerState()
  {
    return playerState;
  }

  protected final void rejectCapturedInput()
  {
    nextPhaseToRun--;
  }
}
