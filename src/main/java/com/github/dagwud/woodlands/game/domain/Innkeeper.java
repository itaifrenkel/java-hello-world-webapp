package com.github.dagwud.woodlands.game.domain;

public class Innkeeper extends NonPlayerCharacter
{
  Innkeeper(Player ownedBy)
  {
    super(ownedBy);
    setName("the Innkeeper");
  }
}
