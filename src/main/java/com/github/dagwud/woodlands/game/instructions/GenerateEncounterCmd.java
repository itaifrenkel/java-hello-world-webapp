package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class GenerateEncounterCmd extends AbstractCmd
{
  private GameState gameState;

  GenerateEncounterCmd(GameState gameState)
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

      RunLaterCmd nextEncounter = new RunLaterCmd(30000, new GenerateEncounterCmd(gameState));
      CommandDelegate.execute(nextEncounter);
    }
  }
}
