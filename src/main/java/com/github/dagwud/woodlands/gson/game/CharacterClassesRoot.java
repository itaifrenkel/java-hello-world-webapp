package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharacterClassesRoot implements Serializable
{
  @SerializedName("classes")
  public CharacterClass[] characterClasses;
}
