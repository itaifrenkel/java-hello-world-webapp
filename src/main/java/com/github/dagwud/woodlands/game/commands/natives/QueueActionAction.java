package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused") // called at runtime via reflection
public class QueueActionAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    String delay = gameState.getVariables().lookupVariableValue("delay");
    long timerDurationMS = determineDurationMS(delay);
    String procToInvoke = gameState.getVariables().lookupVariableValue("ProcName");
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    schedule(Integer.parseInt(chatId), procToInvoke, timerDurationMS);

    return new InvocationResults(new Variables());
  }

  long determineDurationMS(String delay)
  {
    long quantity = 0;
    String units = null;
    char[] chars = delay.toCharArray();
    for (int i = 0; i < chars.length; i++)
    {
      char c = chars[i];
      if (c != ' ' && units == null)
      {
        try
        {
          int v = Integer.parseInt(Character.toString(c));
          quantity = (quantity * 10) + v;
        }
        catch (NumberFormatException e)
        {
          units = delay.substring(i);
        }
      }
    }

    if (units == null)
    {
      throw new WoodlandsRuntimeException("No unit of time (e.g. MS, S, M, H) provided for '" + delay + "'");
    }
    TimeUnit unit = determineTimeUnit(units);
    return TimeUnit.MILLISECONDS.convert(quantity, unit);
  }

  private TimeUnit determineTimeUnit(String units)
  {
    switch (units.toUpperCase())
    {
      case "MS":
        return TimeUnit.MILLISECONDS;
      case "S":
        return TimeUnit.SECONDS;
      case "M":
        return TimeUnit.MINUTES;
      case "H":
        return TimeUnit.HOURS;
      default:
        throw new WoodlandsRuntimeException("Unknown time unit '" + units + "'");
    }
  }

  private void schedule(int chatId, String procName, long delayMS)
  {
    if (chatId == -1)
    {
      delayMS = 0;
    }
    Callable<String> callable = new CallableProc(delayMS, chatId, procName);

    if (delayMS <= 0)
    {
      try
      {
        callable.call();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("SCHEDULING: " + chatId + "@" + procName);
      FutureTask task = new FutureTask<>(callable);
      // Yes, threads are forbidden in EJB... but the current deployment server isn't actually
      // using an EJB container, so this seems ok.
      new Thread(task).start();
      System.out.println("SCHEDULED: " + chatId + "@" + procName);
    }
  }
}
