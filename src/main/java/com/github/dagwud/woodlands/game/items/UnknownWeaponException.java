package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

public class UnknownWeaponException extends WoodlandsRuntimeException
{
  private static final long serialVersionUID = 1L;

  UnknownWeaponException(String message)
  {
    super(message);
  }
}
