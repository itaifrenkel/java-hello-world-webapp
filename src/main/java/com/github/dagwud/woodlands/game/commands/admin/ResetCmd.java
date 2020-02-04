package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class ResetCmd extends AdminCmd
{
   private static final long serialVersionUID = 1L;

   public ResetCmd(int chatId)
  {
    super(chatId);
  }

  @Override
  public void execute()
  {
    GameStatesRegistry.reset();
  }
}
