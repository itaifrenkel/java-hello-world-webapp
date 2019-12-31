package com.github.dagwud.woodlands.gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Root
{
  @SerializedName("Packages")
  public Package[] packages;

  @Override
  public String toString()
  {
    return "Root{" +
            "packges=" + Arrays.toString(packages) +
            '}';
  }
}
