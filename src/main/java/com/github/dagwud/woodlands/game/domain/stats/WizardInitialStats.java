package com.github.dagwud.woodlands.game.domain.stats;

public class WizardInitialStats extends InitialStats
{
  public WizardInitialStats()
  {
    shortRestDice = 6;
    initialStrength = 9;
    initialStrengthModifier = 1 + strengthBoost;
    initialAgility = 14;
    agilityBoost = 2;
    initialAgilityModifier = 2 + agilityBoost;
    initialConstitution = 15;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 16;
    initialIntelligenceModifierUnused = 3;
    initialWisdomUnused = 13;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 0;
    initialProficiencyBonus = 2;
    initialArmorClass = 12;
    initialInitiative = 2;
    initialSpeedUnused = 30;
    initialHitPoints = 8;
    initialRestDiceFaces = 6;
    weaponMasteryBonusHit.put("Staff", 1);
    weaponMasteryBonusDamage.put("Staff", 2);
    weaponMasteryBonusHit.put("Dagger", 4);
    weaponMasteryBonusDamage.put("Dagger", 2);
  }
}
