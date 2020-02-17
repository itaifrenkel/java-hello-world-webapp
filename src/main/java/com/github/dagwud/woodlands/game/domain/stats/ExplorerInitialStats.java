package com.github.dagwud.woodlands.game.domain.stats;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;

public class ExplorerInitialStats extends InitialStats
{
  private static final long serialVersionUID = 1L;

  public ExplorerInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 13;
    initialStrengthModifier = 1 + strengthBoost;
    initialAgility = 9;
    initialAgilityModifier = 1 + agilityBoost;
    initialConstitution = 14;
    constitutionBoost = 2;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 15;
    initialIntelligenceModifierUnused = 2;
    initialWisdomUnused = 16;
    initialWisdomModifierUnused = 3;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 0;
    initialProficiencyBonus = 2;
    initialArmorClass = 13;
    initialInitiative = 1;
    initialSpeedUnused = 30;
    initialHitPoints = 10;
    initialRestDiceFaces = 8;
    weaponMasteryBonusHit.put("Mace", 3);
    weaponMasteryBonusDamage.put("Mace", 1);
  }

  @Override
  public Item[] getStartingItems()
  {
    return new Item[] {
// TODO           ItemsCacheFactory.instance().getCache().findItem("Scalemail Armor"),
            ItemsCacheFactory.instance().getCache().findItem("Small iron shield"),
            ItemsCacheFactory.instance().getCache().findItem("Mace"),
            ItemsCacheFactory.instance().getCache().findItem("Dagger")
    };
  }
}
