package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;

public class EnterTheMountainCmd extends AbstractCmd
{
  private final PlayerState playerState;

  public EnterTheMountainCmd(PlayerState playerState)
  {
    this.playerState = playerState;
  }

  @Override
  public void execute()
  {
    RunLaterCmd cmd = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS,
            new GenerateMountainEncounterCmd(playerState));
    CommandDelegate.execute(cmd);
  }
}
