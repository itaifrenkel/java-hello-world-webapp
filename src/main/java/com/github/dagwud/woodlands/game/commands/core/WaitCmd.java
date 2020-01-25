package com.github.dagwud.woodlands.game.commands.core;

public class WaitCmd extends AbstractCmd
{
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
}
