package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stat;

public class PatchCharacterCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  private static int uniqueNameIndex = 1; 

  public PatchCharacterCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    if (null == character)
    {
      return;
    }

    if (null != character.getStats())
    {
      fixNegativeStats(character.getStats().getStrength(), "strength");
      fixNegativeStats(character.getStats().getAgility(), "agility");
      fixNegativeStats(character.getStats().getConstitution(), "constitution");
      fixNegativeStats(character.getStats().getMaxMana(), "max mana");
      fixNegativeStats(character.getStats().getMaxHitPoints(), "max HP");
    }
  }

  void fixNegativeStats(Stat stat, String statName)
  {
    if (stat.total() < 0)
    {
      stat.clearBonuses();
      CommandDelegate.execute(new SendAdminMessageCmd("PATCH: negative " + statName + " has been undone for " + character.getName()));
      CommandDelegate.execute(new SendMessageCmd(character, "PATCH: negative " + statName + " has been undone"));
    }
  }

}
