package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;

import java.io.IOException;

public abstract class GameInstruction
{
  GameInstruction()
  {
  }

  public abstract void execute(GameState gameState) throws IOException;
}
