package com.github.dagwud.woodlands.gson.game;

public class Hook
{
  public String name;

  public String type;

  public String trigger;

  public Step[] steps;

  @Override
  public String toString()
  {
    return "Hook{" +
            "name='" + name + '\'' +
            '}';
  }
}
