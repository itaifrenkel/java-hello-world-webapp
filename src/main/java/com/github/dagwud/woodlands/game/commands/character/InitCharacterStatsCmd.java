package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.domain.stats.InitialStats;

public class InitCharacterStatsCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  InitCharacterStatsCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    stats.setLevel(1);
    stats.setState(EState.ALIVE);

    InitialStats classStats = character.getCharacterClass().getInitialStats();

    stats.setHitPoints(classStats.getInitialHitPoints());
    stats.getMaxHitPoints().setBase(classStats.getInitialHitPoints());

    stats.setMana(2);
    stats.getMaxMana().setBase(3);

    stats.setStrength(classStats.getInitialStrength(), classStats.getStrengthBoost());
    stats.setAgility(classStats.getInitialAgility(), classStats.getAgilityBoost());
    stats.setConstitution(classStats.getInitialConstitution(), classStats.getConstitutionBoost());

    stats.setWeaponBonusHit(classStats.getWeaponMasteryBonusHit());
    stats.setWeaponBonusDamage(classStats.getWeaponMasteryBonusDamage());

    stats.setRestPointsMax(classStats.getInitialRestPointsMax());
    stats.setRestPoints(classStats.getInitialRestPoints());
    stats.setRestDiceFace(classStats.getShortRestDice());
  }
}
