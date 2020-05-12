package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class NotInPrivatePartyPrerequisite implements CommandPrerequisite
{

  private PlayerCharacter playerCharacter;

  public NotInPrivatePartyPrerequisite(PlayerCharacter playerCharacter)
  {
    this.playerCharacter = playerCharacter;
  }

  @Override
  public boolean verify()
  {
    boolean publicParty = !playerCharacter.getParty().isPrivateParty();

    if (!publicParty)
    {
      CommandDelegate.execute(new SendMessageCmd(playerCharacter, "Can't play poker by yourself - join a party."));
    }

    return publicParty;
  }
}
