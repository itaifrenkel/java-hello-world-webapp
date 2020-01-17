package com.github.dagwud.woodlands.game.commands;

public class AcceptInputCmd extends AbstractCmd
{
  private final SuspendableCmd receiverCmd;
  private final String capturedInput;

  public AcceptInputCmd(SuspendableCmd receiverCmd, String capturedInput)
  {
    super();
    this.receiverCmd = receiverCmd;
    this.capturedInput = capturedInput;
  }

  @Override
  public void execute()
  {
    receiverCmd.setCapturedInput(capturedInput);
    receiverCmd.execute();
  }

}
