package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.EState;
import java.math.BigDecimal;

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
    new SendMessageCmd(activeCharacter.getPlayedBy().getChatId(), "Aaaaaaah. That hit the spot!").go();
  }

}
