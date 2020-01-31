package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.game.WoodlandsRuntimeException;

public class UnknownWeaponException extends WoodlandsRuntimeException
{
  UnknownWeaponException(String message)
  {
    super(message);
  }
}
