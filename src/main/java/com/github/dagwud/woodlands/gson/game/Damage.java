package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Damage
{
  @SerializedName("dice_count")
  public int diceCount;

  @SerializedName("dice_faces")
  public int diceFaces;
}
