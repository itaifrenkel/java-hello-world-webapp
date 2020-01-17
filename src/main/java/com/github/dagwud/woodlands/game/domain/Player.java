package com.github.dagwud.woodlands.game.domain;

public class Player
{
  private final int chatId;
  private GameCharacter activeCharacter;

  public Player(int chatId)
  {
    this.chatId = chatId;
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
}
