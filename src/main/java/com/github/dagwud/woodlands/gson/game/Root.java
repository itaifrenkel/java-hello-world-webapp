package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Root
{
  @SerializedName("QuickCommands")
  public QuickCommand[] quickCommands;

  @SerializedName("Packages")
  public Package[] packages;
}
