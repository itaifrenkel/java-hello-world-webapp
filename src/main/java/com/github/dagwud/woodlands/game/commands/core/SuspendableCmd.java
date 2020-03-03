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

  protected SuspendableCmd(PlayerState playerState, int numberOfPhases, CommandPrerequisite... prerequisites)
  {
    super(prerequisites);
    this.playerState = playerState;
    this.numberOfPhases = numberOfPhases;
  }

  @Override
  public final void execute()
  {
    if ("/cancel".equals(capturedInput))
    {
      playerState.clearWaitingForInputCmd();
      SendMessageCmd sendMessageCmd = new SendMessageCmd(playerState.getPlayer().getChatId(), "Cancelled.");
      CommandDelegate.execute(sendMessageCmd);
      return;
    }

    executePart(nextPhaseToRun, capturedInput);
    if (nextPhaseToRun == 0)
    {
      playerState.pushWaitingForInputCmd(this);
    }

    nextPhaseToRun++;
    capturedInput = null;

    if (nextPhaseToRun >= numberOfPhases)
    {
      removeSuspendable();
    }
  }

  protected abstract void executePart(int phaseToExecute, String capturedInput);

  protected void removeSuspendable()
  {
    playerState.removeWaitingForInput(this);
    if (playerState.peekWaitingForInputCmd() == null)
    {
      resetMenu(playerState);
    }
  }

  protected void resetMenu(PlayerState playerState)
  {
    ShowMenuCmd showMenuCmd = new ShowMenuCmd(playerState.getPlayer().getActiveCharacter().getLocation().getMenu(), playerState);
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
