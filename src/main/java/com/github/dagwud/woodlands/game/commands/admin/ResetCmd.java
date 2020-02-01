package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class ResetCmd extends AbstractCmd
{
   private static final long serialVersionUID = 1L;

   public ResetCmd()
  {
  }

  @Override
  public void execute()
  {
    GameStatesRegistry.reset();
    PartyRegistry.reset();
  }
}
