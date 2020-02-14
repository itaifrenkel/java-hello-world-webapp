package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class CreateGameStateCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final int chatId;
  private PlayerState createdPlayerState;

  CreateGameStateCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    createdPlayerState = new PlayerState();
    createdPlayerState.setPlayer(new Player(chatId));
  }

  PlayerState getCreatedPlayerState()
  {
    return createdPlayerState;
  }
}
