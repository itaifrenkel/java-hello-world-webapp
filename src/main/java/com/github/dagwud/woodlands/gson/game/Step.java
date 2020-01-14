package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.google.gson.annotations.SerializedName;

public class Step
{
  @SerializedName(value = "ProcName")
  public String procName;

  @SerializedName(value = "ParamMappings")
  public Variables paramMappings;

  @Override
  public String toString()
  {
    return "Step{" +
            "procName='" + procName + '\'' +
            ", paramMappings=" + paramMappings +
            '}';
  }
}
