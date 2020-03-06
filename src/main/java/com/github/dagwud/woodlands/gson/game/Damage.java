package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Damage implements Serializable
{
  private static final long serialVersionUID = 1L;

  @SerializedName("dice_count")
  public int diceCount;

  @SerializedName("dice_faces")
  public int diceFaces;

  public Damage()
  {
  }

  public Damage(Damage copy)
  {
    this.diceCount = copy.diceCount;
    this.diceFaces = copy.diceFaces;
  }

  public float determineAverageRollAmount()
  {
    return ((1f + (float) diceFaces) / 2f) * (float) diceCount;
  }

  public String determineAverageRoll()
  {
    float average = determineAverageRollAmount();
    return new DecimalFormat("#.##").format(average);
  }
}
