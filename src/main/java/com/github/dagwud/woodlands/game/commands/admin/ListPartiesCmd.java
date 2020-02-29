package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ListPartiesCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;

  public ListPartiesCmd(int chatId)
  {
    super(chatId);
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    StringBuilder b = new StringBuilder();
    b.append("All Registered Parties:\n");
    for (Party party : PartyRegistry.listAllParties())
    {
      b.append(party.getName())
              .append(" (").append(party.size()).append(") - ")
              .append(party.getLeader() == null ? "No leader" : Icons.LEADER + party.getLeader().getName())
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
      b.append(" • Chat ").append(player.getChatId()).append(": ");

      if (player.getAllCharacters().isEmpty())
      {
        b.append("no characters").append("\n");
      }
      else
      {
        b.append("\n");
      }
      for (PlayerCharacter character : player.getAllCharacters())
      {
        b.append("   • ").append(character.getName());
        if (!character.isActive())
        {
          b.append(" (inactive)");
        }
        b.append(" • ").append(character.getStats().getState().description);
        b.append("\n");
      }
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, b.toString());
    CommandDelegate.execute(cmd);
  }
}
