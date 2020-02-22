package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

import java.io.IOException;

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
    // best attempt only because users can block bot:
    try
    {
System.out.println("send to " + party.getAlertChatId() + ": " + message);
     MessagingFactory.create().sender().sendMessage(party.getAlertChatId(), message);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
