package com.github.dagwud.woodlands.game.domain.stats;

public class ExplorerInitialStats extends InitialStats
{
  public ExplorerInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 13;
    initialStrengthModifier = 1;
    initialAgility = 9;
    initialAgilityModifier = 1;
    initialConstitution = 14;
    initialConstitutionModifier = 2;
    initialIntelligenceUnused = 15;
    initialIntelligenceModifierUnused = 2;
    initialWisdomUnused = 16;
    initialWisdomModifierUnused = 3;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 0;
    initialProficiencyBonus = 2;
    initialArmorClass = 15;
    initialInitiative = 1;
    initialSpeedUnused = 30;
    initialHitPoints = 10;
    initialHitDice = "1d8";
    weaponMasteryBonusHit.put("Mace", 3);
    weaponMasteryBonusDamage.put("Mace", 1);
  }
}
