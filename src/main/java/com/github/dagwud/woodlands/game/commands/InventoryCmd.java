package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InventoryCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private PlayerCharacter character;

  InventoryCmd(int chatId, PlayerCharacter character)
  {
    super(new CharacterIsSetUpPrecondition(chatId, character));
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    StringBuilder b = new StringBuilder();
    b.append("You are carrying:").append("\n");
    for (String itemInfo : buildItemsList())
    {
      b.append(itemInfo).append("\n");
    }
    SendMessageCmd cmd = new SendMessageCmd(chatId, b.toString());
    CommandDelegate.execute(cmd);
  }

  private Collection<String> buildItemsList()
  {
    Collection<String> itemsList = new ArrayList<>();
    if (character.getCarrying().getCarriedLeft() != null)
    {
      itemsList.add("• " + character.getCarrying().getCarriedLeft().summary(character) + " (drop: /dL)");
    }
    if (character.getCarrying().getCarriedRight() != null)
    {
      itemsList.add("• " + character.getCarrying().getCarriedRight().summary(character) + " (drop: /dR)");
    }
    if (!itemsList.isEmpty())
    {
      itemsList.add("——————————————————");
    }
    List<Weapon> carriedInactive = character.getCarrying().getCarriedInactive();
    for (int i = 0; i < carriedInactive.size(); i++)
    {
      Weapon weapon = carriedInactive.get(i);
      itemsList.add("• " + weapon.summary(character) + " (drop: /d" + i + "; equip: /e" + i + ")");
    }
    return itemsList;
  }

}
