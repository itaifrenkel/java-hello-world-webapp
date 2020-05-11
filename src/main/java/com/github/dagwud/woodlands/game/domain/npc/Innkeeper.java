package com.github.dagwud.woodlands.game.domain.npc;

import com.github.dagwud.woodlands.game.domain.Player;

public class Innkeeper extends NonPlayerCharacter
{
  public Innkeeper(Player ownedBy)
  {
    super(ownedBy);
    setName("the Innkeeper");
  }
}
