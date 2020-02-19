package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;

class TestNestedSuspendableCmd extends SuspendableCmd
{
  private String phase1value;
  private String phase2value;

  public TestNestedSuspendableCmd(PlayerState playerState)
  {
    super(playerState, 3);
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptPhaseValue(1);
        break;
      case 1:
        receivePhase1Value(capturedInput);
        promptPhaseValue(2);
        break;
      case 2:
        receivePhase2Value(capturedInput);
    }
  }

  private void promptPhaseValue(int phase)
  {
    CommandDelegate.execute(new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "Enter phase " + phase + " value"));
  }

  private void receivePhase1Value(String capturedInput)
  {
    phase1value = capturedInput;
  }

  private void receivePhase2Value(String capturedInput)
  {
    phase2value = capturedInput;
  }
}
