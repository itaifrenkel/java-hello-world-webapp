package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ShowPartyInfoCmd extends AbstractCmd
{
  private final int chatId;
  private final PlayerCharacter character;

  ShowPartyInfoCmd(int chatId, PlayerCharacter character)
  {
    super(new CharacterIsSetUpPrecondition(chatId, character));
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    String message = buildMessage();

    SendMessageCmd cmd = new SendMessageCmd(chatId, message);
    CommandDelegate.execute(cmd);
  }

  private String buildMessage()
  {
    if (character.getParty().isPrivateParty())
    {
      return "You're not part of a party";
    }

    StringBuilder message = new StringBuilder();
    message.append(character.getParty().getName()).append(":");
    for (GameCharacter member : character.getParty().getActiveMembers())
    {
      if (message.length() > 0)
      {
        message.append("\n");
      }
      message.append(member.summary()).append(" - ").append(member.getLocation().getDisplayName());
      message.append(" [").append(member.hashCode()).append("]");
    }
    return message.toString();
  }
}
