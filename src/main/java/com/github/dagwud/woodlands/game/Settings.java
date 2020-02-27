package com.github.dagwud.woodlands.game;

import com.amazonaws.regions.Regions;

import java.math.BigDecimal;

public class Settings
{
  public static final int MAX_CHARACTER_NAME_LENGTH = 23;

  public static final BigDecimal DEFAULT_PERCENT_CHANCE_OF_ENCOUNTER = new BigDecimal("50");
  public static final int DELAY_BETWEEN_ENCOUNTERS_MS = 15_000;
  public static final int DELAY_BETWEEN_ROUNDS_MS = 15_000;

  // Automatic mana recovery: recover 1 mana point every 3 hours
  public static final long DELAY_BETWEEN_MANA_RECOVERY = 3 * 60 * 60_000; // 3 hours

  // Automatic HP recovery: recover 20% of what a short rest would give every 45 minutes
  // (given as 10% every 22.5 minutes)
  public static final long DELAY_BETWEEN_HP_RECOVERY = (20 * 60_000) + 30_000; // 22.5 minutes
  public static final BigDecimal SCHEDULED_HP_RECOVERY_PERCENT_OF_SHORT_REST = new BigDecimal("10");

  public static final long SOBER_UP_DELAY_MS = 60_000 * 20;
  public static final long SHORT_REST_DURATION_MS = 60_000 * 5;
  public static final long LONG_REST_DURATION_MS = 60_000 * 60; // 1 hour

  public static final int MAX_UNCLAIMED_PARTY_ITEMS = 5;

  public static final int PROGRESS_BARS_FOR_EXPERIENCE = 10;

  public static final int ADMIN_CHAT = 750694421;
  public static final int NOT_THE_ADMIN_JUST_SOME_OTHER_GUY_CHAT = 82170326;

  public static final String S3_BUCKET_NAME = "woodlands-state";
  public static final Regions S3_REGION = Regions.EU_WEST_2;

  public static final String AUTO_JOIN_PARTY_NAME = null;

  public static final int PETTING_ZOO_MIN_DIFFICULTY = 0;
  public static final double PETTING_ZOO_MAX_DIFFICULTY = 0.9;

  public static final int MOUNTAIN_MIN_DIFFICULTY = 1;
  public static final int MOUNTAIN_MAX_DIFFICULTY = 2;

  public static final int WOODLANDS_MIN_DIFFICULTY = 2;
  public static final int WOODLANDS_MAX_DIFFICULTY = 6;
  public static final int WOODLANDS_TIME_ALLOWED_FOR_PLANNING_MS = 30_000;
  public static final int WOODLANDS_ACTIONS_PER_ROUND = 2;

  public static final int DEEP_WOODS_MIN_DIFFICULTY = 7;
  public static final int DEEP_WOODS_MAX_DIFFICULTY = 9;
  public static final int DEEP_WOODS_TIME_ALLOWED_FOR_PLANNING_MS = 20_000;
  public static final int DEEP_WOODS_ACTIONS_PER_ROUND = 2;

  // Any difficulty - but only dragons:
  public static final int THE_GORGE_MIN_DIFFICULTY = Integer.MIN_VALUE;
  public static final int THE_GORGE_MAX_DIFFICULTY = Integer.MAX_VALUE;
  public static final int THE_GORGE_TIME_ALLOWED_FOR_PLANNING_MS = 20_000;
  public static final int THE_GORGE_ACTIONS_PER_ROUND = 2;

  public static boolean DEVELOPER_MODE = false;
}
