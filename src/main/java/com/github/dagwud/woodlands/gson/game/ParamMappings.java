package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.Map;

public class ParamMappings extends Variables
{
  public ParamMappings()
  {
    super("call-parameters");
  }

  public ParamMappings(Map<String, String> callParameters)
  {
    super("call-parameters", callParameters);
  }
}
