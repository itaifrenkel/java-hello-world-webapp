package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;

import java.io.Serializable;

public class GameMenu implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String prompt;
  private ECommand[] options;

  public String getPrompt()
  {
    return prompt;
  }

  void setPrompt(String prompt)
  {
    this.prompt = prompt;
  }

  public ECommand[] getOptions()
  {
    return options;
  }

  void setOptions(ECommand... options)
  {
    this.options = options;
  }

  public boolean containsOption(String option, PlayerState playerState)
  {
    for (String opt : produceOptions(playerState))
    {
      if (opt.equalsIgnoreCase(option))
      {
        return true;
      }
    }
    return false;
  }

  public String[] produceOptions(PlayerState playerState)
  {
    ECommand[] options = getOptions();
    String[] strings = new String[options.length];

    for (int i = 0; i < options.length; i++)
    {
      ECommand option = options[i];
      strings[i] = option.toString();
    }

    return strings;
  }
}
