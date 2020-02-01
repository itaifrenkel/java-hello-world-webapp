package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

import com.github.dagwud.woodlands.game.*;

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
    b.append("All Registered Parties:\n");
    for (Party party : PartyRegistry.listNames())
    {
      b.append(party.getName())
          .append(" (").append(party.size())
          .append(") - 🎖")
          .append(party.getLeader() == null ? "NoLeader" : party.getLeader().getName())
          .append(":\n");
      for (GameCharacter c : party.getActiveMembers())
      {
        b.append(" * ").append(c.summary()).append(c.getLocation()).append("\n");
      }
      b.append("\n");
    }

    b.append("\nAll Players:\n");
    for (PlayerState p : GameStateRegistry.allPlayerStates())
    {
      b.append(p.getActiveCharacter().getName())
          .append(" - ").append(p.getActiveCharacter().getName())
          .append("\n");
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, b.toString());
    CommandDelegate.execute(cmd);
  }
}
