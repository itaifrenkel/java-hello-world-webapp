package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;

public class ActionParameters extends HashMap<String, String>
{
  ActionParameters(Map<String, String> callParameters)
  {
    this.putAll(callParameters);
  }
}
