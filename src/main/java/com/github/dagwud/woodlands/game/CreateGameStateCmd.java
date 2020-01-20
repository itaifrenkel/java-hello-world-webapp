package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class CreateGameStateCmd extends AbstractCmd
{
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
    createdPlayerState.setPlayer(new Player(chatId, createdPlayerState));
  }

  PlayerState getCreatedPlayerState()
  {
    return createdPlayerState;
  }
}
