package com.github.dagwud.woodlands.game.commands.core;

public class WaitCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int delayMS;

  public WaitCmd(int delayMS)
  {
    this.delayMS = delayMS;
  }

  @Override
  public void execute() throws Exception
  {
    Thread.sleep(delayMS);
  }

  @Override
  public String toString()
  {
    return "WaitCmd{" +
            "delayMS=" + delayMS +
            '}';
  }
}
