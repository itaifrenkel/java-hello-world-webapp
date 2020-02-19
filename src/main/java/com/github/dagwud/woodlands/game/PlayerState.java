package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

import java.io.Serializable;
import java.util.Stack;

public class PlayerState implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Player player;
  private Stack<SuspendableCmd> waitingForInputCmd;
  private GameMenu currentMenu;

  PlayerState()
  {
  }

  public Player getPlayer()
  {
    return player;
  }

  // for convenience:
  public PlayerCharacter getActiveCharacter()
  {
    return getPlayer().getActiveCharacter();
  }

  public void setPlayer(Player player)
  {
    this.player = player;
  }

  public void pushWaitingForInputCmd(SuspendableCmd waitingForInputCmd)
  {
    getWaitingForInputCmd().push(waitingForInputCmd);
  }

  public SuspendableCmd peekWaitingForInputCmd()
  {
    if (getWaitingForInputCmd().isEmpty())
    {
      return null;
    }
    return getWaitingForInputCmd().peek();
  }

  public SuspendableCmd popWaitingForInputCmd()
  {
    return getWaitingForInputCmd().pop();
  }

  public GameMenu getCurrentMenu()
  {
    return currentMenu;
  }

  public void setCurrentMenu(GameMenu currentMenu)
  {
    this.currentMenu = currentMenu;
  }

  public void clearWaitingForInputCmd()
  {
    while (!getWaitingForInputCmd().isEmpty())
    {
      getWaitingForInputCmd().pop();
    }
  }

  private Stack<SuspendableCmd> getWaitingForInputCmd()
  {
    if (null == waitingForInputCmd)
    {
      waitingForInputCmd = new Stack<>();
    }
    return waitingForInputCmd;
  }
}
