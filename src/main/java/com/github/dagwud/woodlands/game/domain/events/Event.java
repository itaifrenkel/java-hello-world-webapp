package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class Event
{
  private PlayerCharacter playerCharacter;

  public Event(PlayerCharacter playerCharacter)
  {
    this.playerCharacter = playerCharacter;
  }

  public PlayerCharacter getPlayerCharacter()
  {
    return playerCharacter;
  }
}
