package com.github.dagwud.woodlands.game.commands.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrunkUpMessageCmd extends AbstractCmd
{
  private static final int AUBERGINE_TARGET = 10000;
  private static final double AUBERGINE_CUTOFF = 0.5;

  private static final String EMOJI_REGEX = "([^\\p{L}\\p{N}\\p{P}\\p{Z}\\n\\r+]+)";
  private static final Pattern AUBERGINE_PATTERN = Pattern.compile(EMOJI_REGEX);

  private String message;
  private String incomingMessage;
  private int drunkeness;


  public DrunkUpMessageCmd(String message, int drunkeness)
  {
    this.incomingMessage = message;
    this.drunkeness = drunkeness;
  }

  @Override
  public void execute()
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
    if (drunkeness > 9)
    {
      message = message.replaceAll("!", "!!!").toUpperCase();
    }

    // drunk enough, bring in the aubergines
    if (drunkeness > AUBERGINE_TARGET)
    {
      StringBuilder result = new StringBuilder(message);
      Matcher matcher = AUBERGINE_PATTERN.matcher(message);
      while (matcher.find())
      {
        if (Math.random() > AUBERGINE_CUTOFF)
        {
          result.replace(matcher.start(1), matcher.end(1), "\uD83C\uDF46");
        }
      }
      message = result.toString();
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
