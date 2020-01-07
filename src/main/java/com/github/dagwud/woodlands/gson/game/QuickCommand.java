package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.google.gson.annotations.SerializedName;

public class QuickCommand
{
  public String command;

  @SerializedName(value = "ProcName")
  public String procName;

  @SerializedName(value = "ParamMappings")
  public Variables paramMappings;
}
