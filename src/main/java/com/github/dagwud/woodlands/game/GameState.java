package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.GameMenu;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.instructions.SuspendableCmd;

public class GameState
{
  private Player player;
  private SuspendableCmd waitingForInputCmd;
  private GameMenu currentMenu;

  GameState()
  {
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

  public GameMenu getCurrentMenu()
  {
    return currentMenu;
  }

  public void setCurrentMenu(GameMenu currentMenu)
  {
    this.currentMenu = currentMenu;
  }
}
