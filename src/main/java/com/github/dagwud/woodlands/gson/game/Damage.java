package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Damage
{
  @SerializedName("dice_count")
  public int diceCount;

  @SerializedName("dice_faces")
  public int diceFaces;

  public String determineAverageRoll()
  {
    float average = ((1f + (float) diceFaces) / 2f) * (float) diceFaces;
    return new DecimalFormat("#.##").format(average);
  }
}
