package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

public class ResetServerInstruction extends GameInstruction
{
  ResetServerInstruction()
  {
  }

  @Override
  public void execute(GameState gameState)
  {
    GameStatesRegistry.reset();
  }
}
