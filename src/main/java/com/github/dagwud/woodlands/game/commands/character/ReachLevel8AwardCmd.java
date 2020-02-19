package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.inventory.SpawnTrinketCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.AmuletOfProtection;

public class ReachLevel8AwardCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public ReachLevel8AwardCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    CommandDelegate.execute(new AllowStatsPointUpgradeCmd(character));
    CommandDelegate.execute(new SpawnTrinketCmd(character, new AmuletOfProtection()));
  }
}
