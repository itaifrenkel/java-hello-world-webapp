package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

public class Alchemist extends Crafter<Weapon>
{
  private static final long serialVersionUID = 1L;

  Alchemist()
  {
    super(null);
    setName("the Alchemist");
  }

}
