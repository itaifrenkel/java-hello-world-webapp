package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public enum ECharacterClass implements Serializable
{
  TRICKSTER("Trickster", new TricksterInitialStats()),
  EXPLORER("Explorer", new ExplorerInitialStats()),
  BRAWLER("Brawler", new BrawlerInitialStats()),
  WIZARD("Wizard", new WizardInitialStats()),
  DRUID("Druid", new DruidInitialStats()),
  GENERAL("General", new GeneralInitialStats());

  private final InitialStats initialStats;
  private final String displayName;

  ECharacterClass(String displayName, InitialStats initialStats)
  {
    this.displayName = displayName;
    this.initialStats = initialStats;
  }

  public static ECharacterClass of(String name)
  {
    return ECharacterClass.valueOf(name.toUpperCase());
  }

  public InitialStats getInitialStats()
  {
    return initialStats;
  }

  @Override
  public String toString()
  {
    return displayName;
  }

}
