package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class FinishDrinkingCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter activeCharacter;

  public FinishDrinkingCmd(PlayerCharacter activeCharacter)
  {
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    activeCharacter.getStats().setState(EState.ALIVE);
    CommandDelegate.execute(new SendMessageCmd(activeCharacter, "Aaaaaaah. That hit the spot!"));
  }

}
