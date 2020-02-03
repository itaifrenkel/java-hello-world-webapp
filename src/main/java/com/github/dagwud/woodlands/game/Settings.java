package com.github.dagwud.woodlands.game;

import com.amazonaws.regions.Regions;

import java.math.BigDecimal;

public class Settings
{
  public static final int DELAY_BETWEEN_ENCOUNTERS_MS = 15000;
  public static final int DELAY_BETWEEN_ROUNDS_MS = 15000;
  public static final BigDecimal DEFAULT_PERCENT_CHANCE_OF_ENCOUNTER = new BigDecimal("50");
  public static final long SOBER_UP_DELAY_MS = 60_000;
  public static final int ADMIN_CHAT = 750694421;
  public static final String S3_BUCKET_NAME = "woodlands-state";
  public static final Regions S3_REGION = Regions.EU_WEST_2;
  public static final String AUTO_JOIN_PARTY_NAME = "BetaParty";
}
