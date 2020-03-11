package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class SparringEvent extends Event
{
  private GameCharacter loser;

  public SparringEvent(PlayerCharacter winner, PlayerCharacter loser)
  {
    super(winner);
    this.loser = loser;
  }

  public GameCharacter getLoser()
  {
    return loser;
  }
}
