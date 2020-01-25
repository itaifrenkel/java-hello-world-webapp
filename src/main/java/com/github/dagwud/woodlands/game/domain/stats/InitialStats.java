package com.github.dagwud.woodlands.game.domain.stats;

import java.util.HashMap;
import java.util.Map;

public abstract class InitialStats
{
  int shortRestDice;
  int initialStrength;
  int strengthBoost;
  int initialStrengthModifier;
  int initialAgility;
  int agilityBoost;
  int initialAgilityModifier;
  int initialConstitution;
  int constitutionBoost;
  int initialConstitutionModifier;
  int initialIntelligenceUnused;
  int initialIntelligenceModifierUnused;
  int initialWisdomUnused;
  int initialWisdomModifierUnused;
  int initialCharismaUnused;
  int initialCharismaModifierUnused;
  int initialProficiencyBonus;
  int initialArmorClass;
  int initialInitiative;
  int initialSpeedUnused;
  int initialHitPoints;
  String initialHitDice;
  int initialRestPoints = 1;
  int initialRestPointsMax = 1;
  int initialRestDiceFaces;

  // todo map string -> enum for weapon
  final Map<String, Integer> weaponMasteryBonusHit;
  final Map<String, Integer> weaponMasteryBonusDamage;

  InitialStats()
  {
    weaponMasteryBonusHit = new HashMap<>();
    weaponMasteryBonusDamage = new HashMap<>();
  }

  public int getShortRestDice()
  {
    return shortRestDice;
  }

  public int getInitialStrength()
  {
    return initialStrength;
  }

  public int getStrengthBoost()
  {
    return strengthBoost;
  }

  public int getInitialAgility()
  {
    return initialAgility;
  }

  public int getAgilityBoost()
  {
    return agilityBoost;
  }

  public int getInitialAgilityModifier()
  {
    return initialAgilityModifier;
  }

  public int getInitialConstitution()
  {
    return initialConstitution;
  }

  public int getConstitutionBoost()
  {
    return constitutionBoost;
  }

  public int getInitialConstitutionModifier()
  {
    return initialConstitutionModifier;
  }

  public int getInitialIntelligenceUnused()
  {
    return initialIntelligenceUnused;
  }

  public int getInitialIntelligenceModifierUnused()
  {
    return initialIntelligenceModifierUnused;
  }

  public int getInitialWisdomUnused()
  {
    return initialWisdomUnused;
  }

  public int getInitialWisdomModifierUnused()
  {
    return initialWisdomModifierUnused;
  }

  public int getInitialCharismaUnused()
  {
    return initialCharismaUnused;
  }

  public int getInitialCharismaModifierUnused()
  {
    return initialCharismaModifierUnused;
  }

  public int getInitialProficiencyBonus()
  {
    return initialProficiencyBonus;
  }

  public int getInitialArmorClass()
  {
    return initialArmorClass;
  }

  public int getInitialInitiative()
  {
    return initialInitiative;
  }

  public int getInitialSpeedUnused()
  {
    return initialSpeedUnused;
  }

  public int getInitialHitPoints()
  {
    return initialHitPoints;
  }

  public String getInitialHitDice()
  {
    return initialHitDice;
  }

  public int getInitialRestPoints()
  {
    return initialRestPoints;
  }
  
  public int getInitialRestPointsMax()
  {
    return initialRestPointsMax;
  }

  public int getInitialRestDiceFaces()
  {
    return initialRestDiceFaces;
  }

  public Map<String, Integer> getWeaponMasteryBonusHit()
  {
    return weaponMasteryBonusHit;
  }

  public Map<String, Integer> getWeaponMasteryBonusDamage()
  {
    return weaponMasteryBonusDamage;
  }
}
