package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ShowPartyInfoCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

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
      String state = member.getStats().getState() == EState.ALIVE ? "" : " (" + member.getStats().getState() + ")";
      String charClass = "";
      if (member instanceof PlayerCharacter)
      {
        charClass = ((PlayerCharacter)member).getCharacterClass() + " ";
      }
      message.append(member.getName()).append(":\n")
          .append(" • L").append(member.getStats().getLevel()).append(" ").append(charClass)
          .append("at ").append(member.getLocation().getDisplayName())
          .append(state).append("\n")
          .append(" •").append(member.summary(false)).append("\n");
    }
    return message.toString();
  }
}
