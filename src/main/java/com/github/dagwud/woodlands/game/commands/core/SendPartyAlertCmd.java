package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

public class SendPartyAlertCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Party party;
  private final String message;

  public SendPartyAlertCmd(Party party, String message)
  {
    this.party = party;
    this.message = message;
  }

  @Override
  public void execute()
  {
    if (party == null || party.getAlertChatId() == null)
    {
      return;
    }

    // best attempt only because users can block bot:
    try
    {
      MessagingFactory.create().sender().sendMessage(party.getAlertChatId(), message);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
