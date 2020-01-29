package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class SwitchCharacterCmd extends AbstractCmd
{
  private final Player player;
  private final PlayerCharacter toActivate;

  SwitchCharacterCmd(Player player, PlayerCharacter toActivate)
  {
    this.player = player;
    this.toActivate = toActivate;
  }

  @Override
  public void execute()
  {
    PlayerCharacter wasActive = player.getActiveCharacter();
    if (wasActive != null)
    {
      if (wasActive == toActivate)
      {
        return;
      } 
      if (!toActivate.isCounscious())
      {
        SendMessageCmd err = new SendMessageCmd(player.getChatId(), toActivate.getName() + " is " + toActivate.getStats().getState());
        CommandDelegate.execute(err);
        return;
      }

      if (wasActive.isSetupComplete())
      {
        player.getInactiveCharacters().add(wasActive);
      }
    }

    player.getInactiveCharacters().remove(toActivate);
    player.setActiveCharacter(toActivate);
  }
}
