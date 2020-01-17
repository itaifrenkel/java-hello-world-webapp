package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;

public abstract class SuspendableCmd extends AbstractCmd
{
  private final GameState gameState;
  private final int numberOfPhases;
  private int nextPhaseToRun;
  private String capturedInput;

  SuspendableCmd(GameState gameState, int numberOfPhases)
  {
    this.gameState = gameState;
    this.numberOfPhases = numberOfPhases;
  }

  @Override
  public final void execute()
  {
    executePart(nextPhaseToRun, capturedInput);
    if (nextPhaseToRun == 0)
    {
      gameState.setWaitingForInputCmd(this);
    }
    nextPhaseToRun++;
    capturedInput = null;
    if (nextPhaseToRun == numberOfPhases)
    {
      gameState.setWaitingForInputCmd(null);
    }
  }

  abstract void executePart(int phaseToExecute, String capturedInput);

  void setCapturedInput(String input)
  {
    this.capturedInput = input;
  }

  GameState getGameState()
  {
    return gameState;
  }
}
