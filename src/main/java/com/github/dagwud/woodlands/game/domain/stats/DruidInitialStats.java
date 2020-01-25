package com.github.dagwud.woodlands.game.domain.stats;

public class DruidInitialStats extends InitialStats
{
  public DruidInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 11;
    strengthBoost = 2;
    initialStrengthModifier = 0 + strengthBoost;
    initialAgility = 14;
    initialAgilityModifier = 2+ agilityBoost;
    initialConstitution = 15;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 9;
    initialIntelligenceModifierUnused = 1;
    initialWisdomUnused = 16;
    initialWisdomModifierUnused = 3;
    initialCharismaUnused = 13;
    initialCharismaModifierUnused = 1;
    initialProficiencyBonus = 2;
    initialArmorClass = 15;
    initialInitiative = 2;
    initialSpeedUnused = 30;
    initialHitPoints = 10;
    initialHitDice = "1d8";
    initialRestDiceFaces = 8;
    weaponMasteryBonusHit.put("Scimitar", 4);
    weaponMasteryBonusDamage.put("Scimitar", 2);
    weaponMasteryBonusHit.put("Dagger", 4);
    weaponMasteryBonusDamage.put("Dagger", 2);
    weaponMasteryBonusHit.put("Slingshot", 4);
    weaponMasteryBonusDamage.put("Slingshot", 2);
  }
}
