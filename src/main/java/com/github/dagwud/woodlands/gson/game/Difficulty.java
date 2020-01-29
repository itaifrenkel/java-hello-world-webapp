package com.github.dagwud.woodlands.gson.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Difficulty implements Serializable
{
  public double difficulty;

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
