package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

public class ListPartiesCmd extends AbstractCmd
{
  private final int chatId;

  ListPartiesCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    StringBuilder b = new StringBuilder();
    for (Party listName : PartyRegistry.listNames())
    {
      b.append("- ").append(listName);
    }
    SendMessageCmd cmd = new SendMessageCmd(chatId, b.toString());
    CommandDelegate.execute(cmd);
  }
}
