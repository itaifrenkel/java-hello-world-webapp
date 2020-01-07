package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Root
{
  @SerializedName("Commands")
  public QuickCommand[] quickCommands;

  @SerializedName("Packages")
  public Package[] packages;
}
