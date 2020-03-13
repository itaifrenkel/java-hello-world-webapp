package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.domain.*;

public class LookCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final ELocation location;
  private final int chatId;
  private final PlayerState playerState;

  public LookCmd(int chatId, PlayerCharacter character)
  {
    this.chatId = chatId;
    this.location = character.getLocation();
    this.playerState = character.getPlayedBy().getPlayerState();
  }

  @Override
  public void execute()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, location.getLookText());
    CommandDelegate.execute(cmd);

    StringBuilder otherPartyMembersHere = new StringBuilder();
    if (playerState.getActiveCharacter().getParty() != null)
    {
      for (Fighter activeMember : playerState.getActiveCharacter().getParty().getActiveMembers())
      {
        if (activeMember != playerState.getActiveCharacter() && activeMember.getLocation() == location)
        {
          otherPartyMembersHere.append(activeMember.getName()).append(", ");
        }
      }
    }

    if (otherPartyMembersHere.length() != 0)
    {
      SendMessageCmd partyMembersCmd = new SendMessageCmd(chatId, "Other party members here: \n" + otherPartyMembersHere.substring(0, otherPartyMembersHere.length() - 2));
      CommandDelegate.execute(partyMembersCmd);
    }

    ShowMenuCmd showMenuCmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(showMenuCmd);
  }
}
