package com.github.dagwud.woodlands.game.domain;

public class Blacksmith extends NonPlayerCharacter
{
  private static final long serialVersionUID = 1L;
  private boolean busyCrafting;

  Blacksmith()
  {
    super(null);
    setName("the Blacksmith");
  }

  public void setBusyCrafting(boolean busyCrafting)
  {
    this.busyCrafting = busyCrafting;
  }

  public boolean isBusyCrafting()
  {
    return busyCrafting;
  }
}
