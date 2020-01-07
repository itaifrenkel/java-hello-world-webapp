package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Step
{
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  @SerializedName(value = "ProcName")
  public String procName;

  @SerializedName(value = "ParamMappings")
  public Variables paramMappings;

  @SerializedName(value = "OutputMappings")
  public Variables outputMappings;

  @SerializedName(value = "Chance")
  public String chance;

  public BigDecimal determineChanceRatio()
  {
    if (chance == null)
    {
      return BigDecimal.ONE;
    }
    String c = chance.trim();
    if (c.endsWith("%"))
    {
      return new BigDecimal(c.substring(0, c.length() - 1)).divide(ONE_HUNDRED, RoundingMode.HALF_UP);
    }
    throw new IllegalArgumentException("Unrecognizable chance format: \"" + c + "\"");
  }

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
