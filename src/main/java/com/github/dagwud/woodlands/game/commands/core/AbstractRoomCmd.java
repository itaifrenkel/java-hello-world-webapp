package com.github.dagwud.woodlands.game.commands.core;

public abstract class AbstractRoomCmd extends AbstractCmd
{
  private long interval;

  public AbstractRoomCmd(long interval)
  {
    this.interval = interval;
  }

  public AbstractRoomCmd(long interval, CommandPrerequisite... prerequisites)
  {
    this.interval = interval;

    if (prerequisites.length != 0)
    {
      throw new RuntimeException("Room commands cannot have prerequisites.");
    }
  }

  public long getInterval()
  {
    return interval;
  }
}
