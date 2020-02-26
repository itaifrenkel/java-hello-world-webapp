package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;

public abstract class PeriodicCmd<T extends AbstractCmd> extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final long runEveryMS;
  private final T timerTickCmd;

  public PeriodicCmd(long runEveryMS, T timerTickCmd)
  {
    this.runEveryMS = runEveryMS;
    this.timerTickCmd = timerTickCmd;
  }

  @Override
  public final void execute()
  {
    if (null == timerTickCmd)
    {
      return;
    }

    if (!shouldCancelPeriodicTimer())
    {
      CommandDelegate.execute(timerTickCmd);
      CommandDelegate.execute(new RunLaterCmd(runEveryMS, this));
    }
  }

  protected abstract boolean shouldCancelPeriodicTimer();
}
