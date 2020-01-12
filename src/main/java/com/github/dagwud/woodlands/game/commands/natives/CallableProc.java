package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.instructions.RunProcInstruction;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.concurrent.Callable;

class CallableProc implements Callable<String>
{
  private final long delayMS;
  private final int chatId;
  private final String procName;

  CallableProc(long delayMS, int chatId, String procName)
  {
    this.delayMS = delayMS;
    this.chatId = chatId;
    this.procName = procName;
  }

  @Override
  public String call() throws Exception
  {
    System.out.println("CALLED: " + chatId + "@" + procName);
    System.out.println("WAITING: " + delayMS);
    Thread.sleep(delayMS);
    System.out.println("DONE WAITING!");
    timerLapse();
    return "";
  }

  private void timerLapse()
  {
    try
    {
      runProc();
    }
    catch (ActionInvocationException e)
    {
      // can't throw exception out of timer:
      System.err.println("Error invoking timer " + chatId + "@" + procName);
      e.printStackTrace();
      try
      {
        TelegramMessageSender.sendMessage(chatId, "Something went wrong in timer... sorry");
      }
      catch (IOException ex)
      {
        System.err.println("Something went wrong notifying user about problem with invoking timer");
        ex.printStackTrace();
      }
    }
  }

  private void runProc() throws ActionInvocationException
  {
    System.out.println("INVOKING " + chatId + "@" + procName);
    GameState gameState = GameStatesRegistry.lookup(chatId);
    RunProcInstruction instruction = new RunProcInstruction(procName);
    instruction.execute(gameState);
    System.out.println("DONE INVOKING: " + chatId + "@" + procName);
  }

}
