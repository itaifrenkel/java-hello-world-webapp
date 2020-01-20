package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.PlayerState;

public class Player
{
  private final int chatId;
  private final PlayerState playerState;
  private GameCharacter activeCharacter;

  public Player(int chatId, PlayerState state)
  {
    this.chatId = chatId;
    this.playerState = state;
  }

  public int getChatId()
  {
    return chatId;
  }

  public GameCharacter getActiveCharacter()
  {
    return activeCharacter;
  }

  public void setActiveCharacter(GameCharacter activeCharacter)
  {
    this.activeCharacter = activeCharacter;
  }

  public PlayerState getPlayerState()
  {
    return playerState;
  }
}
