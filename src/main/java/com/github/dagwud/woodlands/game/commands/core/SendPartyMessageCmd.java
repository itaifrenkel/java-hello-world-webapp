package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

public class SendPartyMessageCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Party party;
  private final String message;

  public SendPartyMessageCmd(Party party, String message)
  {
    this.party = party;
    this.message = message;
  }

  @Override
  public void execute()
  {
    for (PlayerCharacter member : party.getActivePlayerCharacters())
    {
      SendMessageCmd send = new SendMessageCmd(member.getPlayedBy().getChatId(), message);
      CommandDelegate.execute(send);
    }
  }
}
