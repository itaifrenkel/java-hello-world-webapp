package com.github.dagwud.woodlands.game.domain.stats;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;

public class TricksterInitialStats extends InitialStats
{
  private static final long serialVersionUID = 1L;

  public TricksterInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 11;
    initialStrengthModifier = 0 + strengthBoost;
    initialAgility = 16;
    agilityBoost = 2;
    initialAgilityModifier = 3 + agilityBoost;
    initialConstitution = 14;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 15;
    initialIntelligenceModifierUnused = 2;
    initialWisdomUnused = 9;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 1;
    initialProficiencyBonus = 2;
    initialArmorClass = 14;
    initialInitiative = 3;
    initialSpeedUnused = 30;
    initialHitPoints = 10;
    initialRestDiceFaces = 8;
    weaponMasteryBonusHit.put("Katana", 5);
    weaponMasteryBonusDamage.put("Katana", 3);
    weaponMasteryBonusHit.put("Dart Gun", 5);
    weaponMasteryBonusDamage.put("Dart Gun", 3);
    weaponMasteryBonusHit.put("Dagger", 5);
    weaponMasteryBonusDamage.put("Dagger", 3);
  }

  @Override
  public Item[] getStartingItems()
  {
    return new Item[] {
            //TODO Leather Armor
            ItemsCacheFactory.instance().getCache().findItem("Dagger"),
            ItemsCacheFactory.instance().getCache().findItem("Dagger"),
            ItemsCacheFactory.instance().getCache().findItem("Crossbow")
    };
  }
}
