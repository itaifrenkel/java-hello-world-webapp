package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharacterClassesRoot implements Serializable
{
  private static final long serialVersionUID = 1L;

  @SerializedName("classes")
  public CharacterClass[] characterClasses;
}
