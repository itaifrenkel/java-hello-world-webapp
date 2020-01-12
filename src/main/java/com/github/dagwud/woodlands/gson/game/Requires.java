package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Requires
{
  public String condition;

  public Step[] steps;

  @SerializedName("or-else")
  public OrElse orElse;
}
