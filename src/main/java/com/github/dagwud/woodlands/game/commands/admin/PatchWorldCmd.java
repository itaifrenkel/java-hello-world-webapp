package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PatchWorldCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  @Override
  public void execute()
  {
    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      patch(playerState.getPlayer().getActiveCharacter());

      for (PlayerCharacter inactiveCharacter : playerState.getPlayer().getInactiveCharacters())
      {
        patch(inactiveCharacter);
      }
    }
  }

  private void patch(PlayerCharacter character)
  {
    CommandDelegate.execute(new PatchCharacterCmd(character));
  }
}
