package com.github.dagwud.woodlands.game.domain.menu;

public class GameMenu
{
  private String prompt;
  private String[] options;

  public String getPrompt()
  {
    return prompt;
  }

  void setPrompt(String prompt)
  {
    this.prompt = prompt;
  }

  public String[] getOptions()
  {
    return options;
  }

  void setOptions(String[] options)
  {
    this.options = options;
  }

  public boolean containsOption(String option)
  {
    for (String opt : options)
    {
      if (opt.equals(option))
      {
        return true;
      }
    }
    return false;
  }
}
