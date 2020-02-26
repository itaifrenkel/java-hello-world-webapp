package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;

public abstract class PeriodicCmd<T extends AbstractCmd> extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final long runEveryMS;

  public PeriodicCmd(long runEveryMS)
  {
    this.runEveryMS = runEveryMS;
  }

  @Override
  public final void execute()
  {
    if (shouldCancelPeriodicTimer())
    {
      return;
    }

    if (shouldRunSingle())
    {
      T timerTickCmd = createSingleRunCmd();
      CommandDelegate.execute(timerTickCmd);
    }
    CommandDelegate.execute(new RunLaterCmd(runEveryMS, this));
  }

  protected abstract boolean shouldRunSingle();

  protected abstract boolean shouldCancelPeriodicTimer();

  protected abstract T createSingleRunCmd();
}
