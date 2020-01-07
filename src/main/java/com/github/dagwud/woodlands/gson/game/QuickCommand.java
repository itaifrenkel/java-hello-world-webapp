package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class QuickCommand
{
  public String command;

  @SerializedName(value = "ProcName")
  public String procName;
}
