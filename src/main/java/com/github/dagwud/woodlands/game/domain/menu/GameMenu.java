package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

import java.io.Serializable;

public class GameMenu implements Serializable
{
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

  public boolean containsOption(String option)
  {
    for (ECommand opt : options)
    {
      if (opt.matches(option))
      {
        return true;
      }
    }
    return false;
  }
}
