package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

public class UnknownWeaponException extends WoodlandsRuntimeException
{
  UnknownWeaponException(String message)
  {
    super(message);
  }
}
