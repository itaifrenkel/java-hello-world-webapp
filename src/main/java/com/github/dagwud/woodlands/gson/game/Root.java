package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Root
{
  @SerializedName("Packages")
  public Package[] packages;

  @SerializedName("Hooks")
  public Hook[] hooks;
}
