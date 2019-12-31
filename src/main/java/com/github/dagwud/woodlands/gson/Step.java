package com.github.dagwud.woodlands.gson;

import com.google.gson.annotations.SerializedName;

public class Step
{
  @SerializedName(value = "ProcName")
  public String procName;

  @SerializedName(value = "ParamMappings")
  public ParamMappings paramMappings;

  @Override
  public String toString()
  {
    return "Step{" +
            "procName='" + procName + '\'' +
            ", paramMapping=" + paramMappings +
            '}';
  }
}
