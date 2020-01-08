package com.github.dagwud.woodlands.gson.game;

import java.util.Arrays;

public class Action
{
  public String name;
  public Requires requires;
  public Step[] steps;

  @Override
  public String toString()
  {
    return "Action{" +
            "procName='" + name + '\'' +
            ", steps=" + Arrays.toString(steps) +
            '}';
  }
}
