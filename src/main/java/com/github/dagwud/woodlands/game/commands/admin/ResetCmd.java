package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ResetCmd extends AbstractCmd
{
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
