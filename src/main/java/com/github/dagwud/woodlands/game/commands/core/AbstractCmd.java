package com.github.dagwud.woodlands.game.commands.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractCmd implements Serializable
{
  private Collection<CommandPrerequisite> prerequisites;

  public AbstractCmd()
  {
    prerequisites = new ArrayList<>(0);
  }

  protected AbstractCmd(CommandPrerequisite... prerequisites)
  {
    this.prerequisites = new ArrayList<>(prerequisites.length);
    Collections.addAll(this.prerequisites, prerequisites);
  }

  public final boolean verifyPrerequisites()
  {
    for (CommandPrerequisite prerequisite : prerequisites)
    {
      if (!prerequisite.verify())
      {
        System.out.println("Command " + getClass().getSimpleName() + " skipped: false result from " + prerequisite.getClass().getSimpleName());
        return false;
      }
    }
    System.out.println("Command " + getClass().getSimpleName());
    return true;
  }

  public abstract void execute() throws Exception;
}
