package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.UnknownActionException;

class UnknownNativeActionException extends UnknownActionException
{
  UnknownNativeActionException(String message)
  {
    super(message);
  }
}
