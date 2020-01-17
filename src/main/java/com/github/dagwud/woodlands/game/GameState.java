package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.instructions.SuspendableCmd;

public class GameState
{
  private final VariableStack variables;
  private Player player;
  private SuspendableCmd waitingForInputCmd;

  GameState()
  {
    variables = new VariableStack();
  }

  public VariableStack getVariables()
  {
    return variables;
  }

  public Player getPlayer()
  {
    return player;
  }

  // for convenience:
  public GameCharacter getActiveCharacter()
  {
    return getPlayer().getActiveCharacter();
  }

  public void setPlayer(Player player)
  {
    this.player = player;
  }

  public void setWaitingForInputCmd(SuspendableCmd waitingForInputCmd)
  {
    this.waitingForInputCmd = waitingForInputCmd;
  }

  public SuspendableCmd getWaitingForInputCmd()
  {
    return waitingForInputCmd;
  }
}
