package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.domain.stats.InitialStats;

public class InitCharacterStatsCmd extends AbstractCmd
{
  private final GameCharacter character;

  public InitCharacterStatsCmd(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = new Stats();
    stats.setLevel(1);
    stats.setState(EState.ALIVE);

    InitialStats classStats = character.getCharacterClass().getInitialStats();

    stats.setHitPoints(classStats.getInitialHitPoints());
    stats.setMaxHitPoints(classStats.getInitialHitPoints());

    stats.setMana(2);
    stats.setMaxMana(3);

    stats.setStrength(classStats.getInitialStrength(), classStats.getStrengthBoost());
    stats.setAgility(classStats.getInitialAgility(), classStats.getAgilityBoost());
    stats.setConstitution(classStats.getInitialConstitution(), classStats.getConstitutionBoost());

    stats.setWeaponBonusHit(classStats.getWeaponMasteryBonusHit());
    stats.setWeaponBonusDamage(classStats.getWeaponMasteryBonusDamage());

    stats.setRestPointsMax(classStats.getInitialRestPointsMax());
    stats.setRestPoints(classStats.getInitialRestPoints());
    stats.setRestDiceFace(classStats.getShortRestDice());

    character.setStats(stats);
  }
}
