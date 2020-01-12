package com.github.dagwud.woodlands.game.characterclasses;

import com.github.dagwud.woodlands.game.WoodlandsException;

class UnknownCharacterClassException extends WoodlandsException
{
  UnknownCharacterClassException(String message)
  {
    super(message);
  }
}
