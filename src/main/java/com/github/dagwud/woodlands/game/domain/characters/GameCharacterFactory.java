package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.*;

public abstract class GameCharacterFactory
{
  private GameCharacterFactory()
  {
  }

  public static PlayerCharacter create(ECharacterClass characterClass, Player player)
  {
    switch (characterClass)
    {
      case GENERAL:
        return new General(player);
      case TRICKSTER:
        return new Trickster(player);
      case EXPLORER:
        return new Explorer(player);
      case BRAWLER:
        return new Brawler(player);
      case WIZARD:
        return new Wizard(player);
      case DRUID:
        return new Druid(player);
      default:
        throw new WoodlandsRuntimeException("Unimplemented class");
    }
  }
}
