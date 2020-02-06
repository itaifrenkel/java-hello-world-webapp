package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class EquipItemCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private final int chatId;
  private final String dropIndex;

  public EquipItemCmd(PlayerCharacter character, int chatId, String equipIndex)
  {
    this.character = character;
    this.chatId = chatId;
    this.dropIndex = equipIndex;
  }

  @Override
  public void execute()
  {
    Item toEquip;
    try
    {
      int index = Integer.parseInt(dropIndex);
      toEquip = character.getCarrying().getCarriedInactive().get(index);
    }
    catch (NumberFormatException | IndexOutOfBoundsException e)
    {
      toEquip = null;
    }

    if (toEquip != null)
    {
      makeSpace();
      if (character.getCarrying().getCarriedLeft() == null)
      {
        character.getCarrying().setCarriedLeft(toEquip);
      }
      else
      {
        character.getCarrying().setCarriedRight(toEquip);
      }
      character.getCarrying().getCarriedInactive().remove(toEquip);
      SendMessageCmd cmd = new SendMessageCmd(chatId, "Done.");
      CommandDelegate.execute(cmd);

      InventoryCmd inv = new InventoryCmd(chatId, character);
      CommandDelegate.execute(inv);
    }
    else
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "That's not a valid option");
      CommandDelegate.execute(cmd);
    }
  }

  private void makeSpace()
  {
    if (character.getCarrying().getCarriedRight() == null)
    {
      return;
    }
    if (character.getCarrying().getCarriedLeft() == null)
    {
      return;
    }
    Item moveL = character.getCarrying().getCarriedLeft();
    Item moveR = character.getCarrying().getCarriedRight();
    character.getCarrying().getCarriedInactive().add(moveR);
    character.getCarrying().setCarriedRight(moveL);
    character.getCarrying().setCarriedLeft(null);
  }
}
