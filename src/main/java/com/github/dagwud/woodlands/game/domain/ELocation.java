package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.menu.*;

public enum ELocation
{
  VILLAGE_SQUARE("The Village", new VillageMenu()),
  INN("The Inn", new InnMenu()),
  TAVERN("The Tavern", new TavernMenu()),
  MOUNTAIN("The Mountain", new MountainMenu()),
  WOODLANDS("The Woodlands", new WoodlandsMenu());

  private final String displayName;
  private final GameMenu menu;

  ELocation(String displayName, GameMenu menu)
  {
    this.displayName = displayName;
    this.menu = menu;
  }

  public String toString()
  {
    return displayName;
  }

  public GameMenu getMenu()
  {
    return menu;
  }
}
