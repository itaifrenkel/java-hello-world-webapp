package com.github.dagwud.woodlands.game.commands.core;

public class DrunkUpMessageCmd extends AbstractCmd
{
  private String message;
  private String incomingMessage;
  private int drunkeness;

  public DrunkUpMessageCmd(String message, int drunkeness)
  {
    this.incomingMessage = message;
    this.drunkeness = drunkeness;
  }

  @Override
  public void execute() throws Exception
  {
    message = incomingMessage;

    if (drunkeness > 2)
    {
      message = message.replaceAll("s", "ss");
    }
    if (drunkeness > 3)
    {
      message = message.replaceAll("c", "s");
    }
    if (drunkeness > 4)
    {
      message = message.replaceAll("s", "SH");
    }
    if (drunkeness > 5)
    {
      message = message.replaceAll("p", "ph").replaceAll("P", "Ph");
    }
    if (drunkeness > 6)
    {
      message = message.replaceAll("s", "sh").toUpperCase();
    }
    if (drunkeness > 8)
    {
      message = message.replaceAll("\\.", "!").toUpperCase();
    }

    message = message.replaceAll("<B>", "<b>");
    message = message.replaceAll("</B>", "</b>");
    message = message.replaceAll("<I>", "<i>");
    message = message.replaceAll("</I>", "</i>");
    message = message.replaceAll("<U>", "<u>");
    message = message.replaceAll("</U>", "</u>");
  }

  public String getMessage()
  {
    return message;
  }
}
