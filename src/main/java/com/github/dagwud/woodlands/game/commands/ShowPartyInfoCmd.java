package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class ShowPartyInfoCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  ShowPartyInfoCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    StringBuilder message = new StringBuilder();
    for (GameCharacter member : character.getParty().getMembers())
    {
      if (message.length() > 0)
      {
        message.append("\n");
      }
      Stats stats = member.getStats();
      message.append(member.getName()).append(": ")
              .append("❤️").append(stats.getHitPoints()).append(" / ").append(stats.getMaxHitPoints())
              .append(", ").append("✨")
              .append(stats.getMana()).append(" / ").append(stats.getMaxMana())
              .append(" - ").append(member.getLocation().getDisplayName());
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, message.toString());
    CommandDelegate.execute(cmd);
  }
}
