package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.instructions.RunProcInstruction;

import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.naming.NamingException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused") // called at runtime via reflection
public class QueueActionAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails) throws ActionInvocationException
  {
    String delay = gameState.getVariables().lookupVariableValue("delay");
    long timerDurationMS = determineDurationMS(delay);
    String procToInvoke = gameState.getVariables().lookupVariableValue("ProcName");
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    try
    {
      schedule(Long.parseLong(chatId), procToInvoke, timerDurationMS);
    }
    catch (NamingException e)
    {
      throw new ActionInvocationException(e);
    }

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

  public void schedule(long chatId, String procName, long delayMS) throws NamingException, ActionInvocationException
  {
    String timerInfo = chatId + "@" + procName;
    if (chatId == -1)
    {
      timerLapse(String.valueOf(chatId), procName);
    }
    else
    {
      Callable<String> callable = new CallableProc<String>(delayMS, timerInfo);
      FutureTask task = new FutureTask<>(callable);
      System.out.println("SCHEDULING: " + timerInfo);
      new Thread(task).start();
      System.out.println("Timer scheduled - " + delayMS + "ms: run " + procName);
    }
  }

  @Timeout
  public void timerLapse(Timer timer)
  {
    System.out.println("TIMER TICKED!");
    String[] details = ((String)timer.getInfo()).split("@");
    String chatId = details[0];
    String procName = details[1];
    try
    {
      timerLapse(chatId, procName);
    }
    catch (ActionInvocationException e)
    {
      // can't throw exception out of timer:
      System.err.println("Error invoking timer " + timer.getInfo());
      e.printStackTrace();
    }
  }

  private void timerLapse(String chatId, String procName) throws ActionInvocationException
  {
    GameState gameState = GameStatesRegistry.lookup(Integer.parseInt(chatId));
    RunProcInstruction instruction = new RunProcInstruction(procName);
    instruction.execute(gameState);
  }

}
