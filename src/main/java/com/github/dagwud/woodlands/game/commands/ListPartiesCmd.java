package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ListPartiesCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

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
              .append(" (").append(party.size()).append(") - ")
              .append(party.getLeader() == null ? "No leader" : "🎖" + party.getLeader().getName())
              .append("\n");
      for (GameCharacter c : party.getActiveMembers())
      {
        b.append(" • ").append(c.summary())
                .append(" (").append(c.getStats().getState()).append(")")
                .append(" @ ").append(c.getLocation())
                .append("\n");
      }
      b.append("\n");
    }

    b.append("\nAll Players:\n");
    for (PlayerState p : GameStatesRegistry.allPlayerStates())
    {
      Player player = p.getPlayer();
      b.append(" • ").append(player.getChatId()).append("\n");

      for (PlayerCharacter character : player.getAllCharacters())
      {
        b.append("   ").append(character.isActive() ? "⚫" : "•").append(" ").append(character.getName())
                .append(" - ").append(character.getName());
        if (character.isActive())
        {
          b.append(" (active)");
        }
        b.append("\n");
      }
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, b.toString());
    CommandDelegate.execute(cmd);
  }
}
