package com.github.dagwud.woodlands.game.domain.stats;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;

public class GeneralInitialStats extends InitialStats
{
  private static final long serialVersionUID = 1L;

  public GeneralInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 16;
    strengthBoost = 3;
    initialStrengthModifier = 3 + strengthBoost;
    initialAgility = 14;
    initialAgilityModifier = 2 + agilityBoost;
    initialConstitution = 15;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 11;
    initialIntelligenceModifierUnused = 0;
    initialWisdomUnused = 13;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 9;
    initialCharismaModifierUnused = 1;
    initialProficiencyBonus = 2;
    initialArmorClass = 16;
    initialInitiative = 2;
    initialSpeedUnused = 30;
    initialHitPoints = 12;
    initialRestDiceFaces = 10;
    weaponMasteryBonusHit.put("Battle Axe", 5);
    weaponMasteryBonusDamage.put("Battle Axe", 3);
    weaponMasteryBonusHit.put("Javelin", 5);
    weaponMasteryBonusDamage.put("Javelin", 3);
  }

  @Override
  public Item[] getStartingItems()
  {
    return new Item[] {
//            ItemsCacheFactory.instance().getCache().findItem("Chainmail Armor")
            ItemsCacheFactory.instance().getCache().findItem("Large wooden shield"),
            ItemsCacheFactory.instance().getCache().findItem("Battle Axe"),
            ItemsCacheFactory.instance().getCache().findItem("Javelin"),
            ItemsCacheFactory.instance().getCache().findItem("Dagger")
    };
  }
}
