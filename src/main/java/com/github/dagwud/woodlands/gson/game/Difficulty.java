package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

public class Difficulty
{
  public int difficulty;

  @SerializedName("defensive")
  public int defensiveRating;

  public int proficiency;

  @SerializedName("hit-points-min")
  public int minimumHitPoints;

  @SerializedName("hit-points-max")
  public int maximumHitPoints;

  @SerializedName("xp-reward")
  public int experienceReward;
}
