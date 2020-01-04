package com.github.dagwud.woodlands.gson.telegram;

import com.google.gson.annotations.SerializedName;

public class User
{
  public int id;

  @SerializedName("first_name")
  public String firstName;

  @SerializedName("last_name")
  public String lastName;

  @SerializedName("username")
  public String username;
}
