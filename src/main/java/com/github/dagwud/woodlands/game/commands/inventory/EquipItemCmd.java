package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;

public class EquipItemCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private Item toEquip;
  private final int chatId;
  private final String dropIndex;
  private final boolean showInventoryAfter;

  public EquipItemCmd(PlayerCharacter character, int chatId, String equipIndex, boolean showInventoryAfter)
  {
    this.character = character;
    this.chatId = chatId;
    this.dropIndex = equipIndex;
    this.showInventoryAfter = showInventoryAfter;
  }

  public EquipItemCmd(PlayerCharacter character, int chatId, Item toEquip, boolean showInventoryAfter)
  {
    this.character = character;
    this.chatId = chatId;
    this.toEquip = toEquip;
    this.dropIndex = null;
    this.showInventoryAfter = showInventoryAfter;
  }

  @Override
  public void execute()
  {
    determineItemToEquip();

    if (toEquip == null)
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "That's not a valid option");
      CommandDelegate.execute(cmd);
      return;
    }

    if (toEquip instanceof Trinket)
    {
      if (!(toEquip instanceof ConsumableTrinket))
      {
        character.getCarrying().getWorn().add(toEquip);
      }
    }
    else
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
    }
    handleEquip(toEquip);
    character.getCarrying().getCarriedInactive().remove(toEquip);

    if (showInventoryAfter)
    {
      InventoryCmd inv = new InventoryCmd(chatId, character);
      CommandDelegate.execute(inv);
    }
  }

  private void determineItemToEquip()
  {
    if (toEquip != null)
    {
      return;
    }
    try
    {
      int index = Integer.parseInt(dropIndex);
      toEquip = character.getCarrying().getCarriedInactive().get(index);
    }
    catch (NumberFormatException | IndexOutOfBoundsException e)
    {
      toEquip = null;
    }
  }

  private void handleEquip(Item toEquip)
  {
    if (toEquip instanceof Trinket)
    {
      ((Trinket)toEquip).equip(character);
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
    CommandDelegate.execute(new UnequipItemCmd(character, moveR));
  }
}
