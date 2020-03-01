package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.*;

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
    message.append(character.getParty().getName())
        .append(" (").append(character.getParty().getLeader().getLocation().getDisplayName() + "):\n");
    if (!character.getParty().getCollectedItems().isEmpty())
    {
      message.append("Unclaimed Items:\n");
      for (Item collectedItem : character.getParty().getCollectedItems())
      {
        message.append(" • ").append(collectedItem.getName()).append("\n");
      }
    }
    for (GameCharacter member : character.getParty().getActiveMembers())
    {
      String state = member.getStats().getState().icon;
      if (!state.isEmpty())
      {
        state = " (" + state + ")";
      }

      String charClass = "";
      if (member instanceof PlayerCharacter)
      {
        charClass = ((PlayerCharacter)member).getCharacterClass().toString();
      }

      String location = "";
      if (member.getLocation() != character.getParty().getLeader().getLocation())
      {
        location = " (" + member.getLocation().getDisplayName() + ")";
      }

      String levelAndClass = "L" + member.getStats().getLevel() + " " + charClass;
      if (member instanceof PlayerCharacter && character.getParty().isLedBy((PlayerCharacter) member))
      {
        levelAndClass = Icons.LEADER + " " + levelAndClass;
      }

      String weapons = "";
      if (member.getCarrying().getCarriedLeft() != null)
      {
        weapons += member.getCarrying().getCarriedLeft().summary(character, false);
      }
      if (member.getCarrying().getCarriedRight() != null)
      {
        if (!weapons.isEmpty())
        {
          weapons += ", ";
        }
        weapons += member.getCarrying().getCarriedRight().summary(character, false);
      }

      if (message.length() > 0)
      {
        message.append("\n");
      }

      String firstLine = member.getName() + ": " + levelAndClass + location + state + "\n";
      String secondLine = member.summary(false) + "\n";
      String thirdLine = weapons + (weapons.isEmpty() ? "" : "\n");

      message.append(firstLine);
      message.append(secondLine);
      message.append(thirdLine);
    }
    return message.toString();
  }
}
