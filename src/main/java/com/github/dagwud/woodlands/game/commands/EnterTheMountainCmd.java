package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;

public class EnterTheMountainCmd extends AbstractCmd
{
  private final GameState gameState;

  EnterTheMountainCmd(GameState gameState)
  {
    this.gameState = gameState;
  }

  @Override
  public void execute()
  {
    RunLaterCmd cmd = new RunLaterCmd(30000, new GenerateMountainEncounterCmd(gameState));
    CommandDelegate.execute(cmd);
  }
}
