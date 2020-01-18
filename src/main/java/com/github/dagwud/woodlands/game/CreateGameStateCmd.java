package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class CreateGameStateCmd extends AbstractCmd
{
  private final int chatId;
  private GameState createdGameState;

  CreateGameStateCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    createdGameState = new GameState();
    createdGameState.setPlayer(new Player(chatId));
  }

  GameState getCreatedGameState()
  {
    return createdGameState;
  }
}
