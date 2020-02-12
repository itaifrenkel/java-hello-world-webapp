package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

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

    toEquip.doEquip(character);
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

}
