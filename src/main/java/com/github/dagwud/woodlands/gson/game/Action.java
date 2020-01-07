package com.github.dagwud.woodlands.gson.game;

import java.util.Arrays;

public class Action
{
  public String name;
  public Step[] steps;
  public String command;

  @Override
  public String toString()
  {
    return "Action{" +
            "procName='" + name + '\'' +
            ", steps=" + Arrays.toString(steps) +
            '}';
  }
}
