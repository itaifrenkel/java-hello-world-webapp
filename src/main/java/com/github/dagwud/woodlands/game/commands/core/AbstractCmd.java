package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.log.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractCmd implements Serializable
{
  private static final long serialVersionUID = 1L;

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
        Logger.info("Command " + getClass().getSimpleName() + " skipped: false result from " + prerequisite.getClass().getSimpleName());
        return false;
      }
    }
    return true;
  }

  public abstract void execute() throws Exception;

  @Override
  public String toString()
  {
    return getClass().getSimpleName();
  }
}
