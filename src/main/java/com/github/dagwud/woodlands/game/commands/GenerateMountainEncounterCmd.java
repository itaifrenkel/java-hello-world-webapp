package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class GenerateMountainEncounterCmd extends AbstractCmd
{
  private GameState gameState;

  public GenerateMountainEncounterCmd(GameState gameState)
  {
    this.gameState = gameState;
  }

  @Override
  public void execute()
  {
    if (gameState.getActiveCharacter().getLocation() == ELocation.MOUNTAIN)
    {
      SendMessageCmd cmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "Encounter occurs");
      CommandDelegate.execute(cmd);

      RunLaterCmd nextEncounter = new RunLaterCmd(30000, new GenerateMountainEncounterCmd(gameState));
      CommandDelegate.execute(nextEncounter);
    }
  }
}
