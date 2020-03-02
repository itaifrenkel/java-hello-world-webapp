package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.commands.ICommand;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.io.Serializable;

public class GameMenu implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String prompt;
  private ICommand[] options;

  public String getPrompt()
  {
    return prompt;
  }

  protected void setPrompt(String prompt)
  {
    this.prompt = prompt;
  }

  public ICommand[] getOptions()
  {
    return options;
  }

  protected void setOptions(ICommand... options)
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

  public String produceEntryText(PlayerCharacter playerState, ELocation from)
  {
    return null;
  }

  public String produceExitText(PlayerCharacter playerState, ELocation to)
  {
    return null;
  }

  public String[] produceOptions(PlayerState playerState)
  {
    ICommand[] options = getOptions();
    String[] strings = new String[options.length];

    for (int i = 0; i < options.length; i++)
    {
      ICommand option = options[i];
      strings[i] = option.getMenuText();
    }

    return strings;
  }

  public ICommand getOption(String cmd)
  {
    for (ICommand option : getOptions())
    {
      if (option.getMenuText().equals(cmd))
      {
        return option;
      }
    }
    return null;
  }
}
