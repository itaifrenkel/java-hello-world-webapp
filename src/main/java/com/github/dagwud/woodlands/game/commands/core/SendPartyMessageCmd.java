package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

public class SendPartyMessageCmd extends AbstractCmd
{
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
    for (GameCharacter member : party.getActiveMembers())
    {
      SendMessageCmd send = new SendMessageCmd(member.getPlayedBy().getChatId(), message);
      CommandDelegate.execute(send);
    }
  }
}
