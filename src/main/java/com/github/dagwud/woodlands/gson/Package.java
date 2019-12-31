package com.github.dagwud.woodlands.gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Package
{
  @SerializedName(value = "Name")
  public String name;

  public Action[] actions;

  @Override
  public String toString()
  {
    return "Package{" +
            "name='" + name + '\'' +
            ", actions=" + Arrays.toString(actions) +
            '}';
  }
}
