package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AllowStatsPointUpgradeCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public AllowStatsPointUpgradeCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int upgrades = character.getStats().getAvailableStatsPointUpgrades();
    character.getStats().setAvailableStatsPointUpgrades(upgrades + 2);

    CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "<b>Congratulations! You've earned an upgrade to your character stats. Head to The Tavern to celebrate!"));
  }
}
