package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.domain.Player;

public class PlayerSetupCmd extends AbstractCmd
{
  private final Player player;

  public PlayerSetupCmd(Player player)
  {
    super(new NoActiveCharacterPrerequisite(player));
    this.player = player;
  }

  @Override
  public void execute()
  {
    DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(player);
    CommandDelegate.execute(cmd);
  }

  public Player getPlayer()
  {
    return player;
  }
}
