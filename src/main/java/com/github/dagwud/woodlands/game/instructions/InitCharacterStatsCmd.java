package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Stats;
import com.github.dagwud.woodlands.game.domain.stats.InitialStats;

public class InitCharacterStatsCmd extends AbstractCmd
{
  private final GameCharacter character;

  InitCharacterStatsCmd(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = new Stats();
    stats.setLevel(1);

    InitialStats classStats = character.getCharacterClass().getInitialStats();

    stats.setHitPoints(classStats.getInitialHitPoints());
    stats.setMaxHitPoints(classStats.getInitialHitPoints());

    stats.setMana(2);
    stats.setMaxMana(3);

    stats.setStrength(classStats.getInitialStrength());
    stats.setAgility(classStats.getInitialAgility());
    stats.setConstitution(classStats.getInitialConstitution());

    character.setStats(stats);
  }
}
