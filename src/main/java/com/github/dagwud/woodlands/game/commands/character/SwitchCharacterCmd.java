package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class SwitchCharacterCmd extends AbstractCmd
{
  private final Player player;
  private final GameCharacter toActivate;

  SwitchCharacterCmd(Player player, GameCharacter toActivate)
  {
    this.player = player;
    this.toActivate = toActivate;
  }

  @Override
  public void execute()
  {
    GameCharacter wasActive = player.getActiveCharacter();
    if (wasActive != null)
    {
      if (wasActive == toActivate)
      {
        return;
      }
      if (wasActive.isSetupComplete())
      {
        player.getInactiveCharacters().add(wasActive);
      }
      if (toActivate.getStats().getState() != EState.INACTIVE)
      {
        SendMessageCmd err = new SendMessageCmd(player.getChatId(), toActivate.getName() + " is " + toActivate.getStats().getState());
        CommandDelegate.execute(err);
      }
    }

    wasActive.getStats().setState(EState.INACTIVE);

    player.getInactiveCharacters().remove(toActivate);
    player.setActiveCharacter(toActivate);
  }
}
