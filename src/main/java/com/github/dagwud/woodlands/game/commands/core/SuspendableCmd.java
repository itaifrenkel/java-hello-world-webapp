package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;

public abstract class SuspendableCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

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
    if ("/cancel".equals(capturedInput))
    {
      playerState.setWaitingForInputCmd(null);
      SendMessageCmd sendMessageCmd = new SendMessageCmd(playerState.getPlayer().getChatId(), "Cancelled.");
      CommandDelegate.execute(sendMessageCmd);
      return;
    }

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
      resetMenu(playerState);
    }
  }

  protected abstract void executePart(int phaseToExecute, String capturedInput);

  protected void resetMenu(PlayerState playerState)
  {
    ShowMenuCmd showMenuCmd = new ShowMenuCmd(player.getActiveCharacter().getLocation().getMenu(), playerState);
    CommandDelegate.execute(showMenuCmd);
  }

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
