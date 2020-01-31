package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

import java.io.Serializable;

public class PlayerState implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Player player;
  private SuspendableCmd waitingForInputCmd;
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
