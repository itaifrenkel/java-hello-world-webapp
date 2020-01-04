package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Step
{
  @SerializedName(value = "ProcName")
  public String procName;

  @SerializedName(value = "ParamMappings")
  public ParamMappings paramMappings;

  @SerializedName(value = "OutputMappings")
  public ParamMappings outputMappings;

  @Override
  public String toString()
  {
    return "Step{" +
            "procName='" + procName + '\'' +
            ", paramMappings=" + paramMappings +
            ", outputMappings=" + outputMappings +
            '}';
  }
}
