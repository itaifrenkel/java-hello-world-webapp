package com.github.dagwud.woodlands.gson;

import java.util.Arrays;

public class Action
{
  public String name;
  public String[] inputs;
  public Step[] steps;
  public String[] outputs;

  @Override
  public String toString()
  {
    return "Action{" +
            "procName='" + name + '\'' +
            ", inputs=" + Arrays.toString(inputs) +
            ", steps=" + Arrays.toString(steps) +
            ", outputs=" + Arrays.toString(outputs) +
            '}';
  }
}
