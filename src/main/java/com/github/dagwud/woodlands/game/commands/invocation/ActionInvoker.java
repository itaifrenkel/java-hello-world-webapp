package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.conditional.ConditionEvaluator;
import com.github.dagwud.woodlands.game.commands.conditional.ConditionEvaluatorFactory;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;
import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.Step;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class ActionInvoker
{
  private static final String NATIVE_ACTION_PREFIX = "Native:";
  private final String procNameExpression;
  private final GameState gameState;

  private LinkedHashMap<Step, StepInvoker> stepsToRun;
  private Step lastCompleted;

  public ActionInvoker(String procNameExpression, GameState gameState)
  {
    this.procNameExpression = procNameExpression;
    this.gameState = gameState;
  }

  public InvocationResults invokeAction() throws ActionInvocationException
  {
    return invokeAction(new Variables());
  }

  public InvocationResults invokeAction(Variables callDetails) throws ActionInvocationException
  {
    gameState.suspended2 = null;
    gameState.getVariables().pushNewVariablesStackFrame("invoke " + procNameExpression, callDetails);
    int i = 0;
    boolean suspended = false;
    while (!suspended && hasNext()) ///todo
    {
      InvocationResults results = invokeNext();
      if (results.getReturnMode() == ReturnMode.SUSPEND)
      {
        suspended = true;
      }
    }

    if (!suspended)
    {
      gameState.getVariables().dropStackFrame();
    }

    if (suspended)
    {
      gameState.suspended2 = this;
    }
    return new InvocationResults(new Variables()); //todo
  }

  InvocationResults invokeNext() throws ActionInvocationException
  {
    String proc = ValueResolver.resolve(procNameExpression, gameState.getVariables());
    InvocationResults results;
    if (isNativeAction(proc))
    {
      stepsToRun = new LinkedHashMap<>(0); // little bit hackish... just so we can know there are no substeps, so it's effectively complete after one call
      results = invokeNativeAction(proc, gameState);
    }
    else
    {
      Action action = ActionsCacheFactory.instance().findAction(proc);
      results = invokeNextStep(action, gameState);
    }
    return results;
  }

  private static boolean isNativeAction(String procName)
  {
    return procName.startsWith(NATIVE_ACTION_PREFIX);
  }

  private InvocationResults invokeNativeAction(String proc, GameState gameState) throws ActionInvocationException
  {
    proc = proc.substring(NATIVE_ACTION_PREFIX.length());
    NativeAction action = NativeActionResolver.lookupNativeAction(proc);
    CallDetails cd = new CallDetails(new Variables(), new Variables());
    NativeActionInvoker invoker = new NativeActionInvoker(action, cd);
    return invoker.doInvoke(gameState, cd.getOutputMappings());
  }

  boolean hasNext()
  {
    if (stepsToRun == null)
    {
      return true;
    }
    int lastRunIndex = determineLastCompletedStep();
    int nextIndexToRun = lastRunIndex + 1;
    return nextIndexToRun < stepsToRun.size();
  }

  private InvocationResults invokeNextStep(Action action, GameState gameState) throws ActionInvocationException
  {
    if (null == stepsToRun)
    {
      initStepsToRun(action, gameState);
    }
    Step step = determineNextStep();
    StepInvoker stepInvoker = determineStepInvoker(gameState, step);
    InvocationResults results = stepInvoker.invoke();
    if (stepInvoker.isComplete())
    {
      lastCompleted = step;
    }
    return results;
  }

  private StepInvoker determineStepInvoker(GameState gameState, Step step)
  {
    StepInvoker stepInvoker = stepsToRun.get(step);
    if (stepInvoker == null)
    {
      stepInvoker = new StepInvoker(step, gameState);
      stepsToRun.put(step, stepInvoker);
    }
    return stepInvoker;
  }

  private Step determineNextStep()
  {
    int lastRunIndex = determineLastCompletedStep();
    Step[] steps = stepsToRun.keySet().toArray(new Step[0]);
    if (lastRunIndex + 1 > steps.length)
    {
      throw new WoodlandsRuntimeException("Attempt to access invalid step: step " + (lastRunIndex + 1) + " of " + Arrays.toString(steps) + " in " + procNameExpression);
    }
    return steps[lastRunIndex + 1];
  }

  private void initStepsToRun(Action action, GameState gameState)
  {
    Step[] steps = determineConditionalStepsToRun(action, gameState);
    steps = steps == null ? new Step[0] : steps;
    stepsToRun = new LinkedHashMap<>();
    for (Step step : steps)
    {
      stepsToRun.put(step, null);
    }
  }

  private int determineLastCompletedStep()
  {
    if (lastCompleted == null)
    {
      return -1;
    }
    else
    {
      return find(stepsToRun.keySet().toArray(new Step[0]), lastCompleted);
    }
  }

  private int find(Step[] searchIn, Step find)
  {
    for (int i = 0; i < searchIn.length; i++)
    {
      if (searchIn[i] == find)
      {
        return i;
      }
    }
    throw new WoodlandsRuntimeException("Something went wrong... step has vanished");
  }

  private Step[] determineConditionalStepsToRun(Action action, GameState gameState)
  {
    if (action.steps != null && action.requires != null)
    {
      throw new WoodlandsRuntimeException("Actions shouldn't contain conditional and non-conditional steps - the order of execution can't be guaranteed. Rather split them up into smaller actions");
    }

    Step[] steps;
    if (action.requires == null)
    {
      return action.steps;
    }

    //todo flesh out
    ConditionEvaluator evaluator = ConditionEvaluatorFactory.createEvaluator(action.requires.condition, gameState.getVariables());
    if (evaluator.evaluatesToTrue())
    {
      steps = action.requires.steps;
    }
    else
    {
      steps = null == action.requires.orElse ? null : action.requires.orElse.steps;
    }
    return steps;
  }
}
