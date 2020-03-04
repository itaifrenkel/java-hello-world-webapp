package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite; 
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

import java.util.List;

public class DropItemCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private final int chatId;
  private String dropIndex;

  public DropItemCmd(PlayerCharacter character, int chatId, String dropIndex)
  {
    super(new AbleToActPrerequisite(character));
    this.character = character;
    this.chatId = chatId;
    this.dropIndex = dropIndex;
  }

  @Override
  public void execute()
  {
    Item dropped;
    if (dropIndex.equals("L"))
    {
      dropped = character.getCarrying().getCarriedLeft();
      CommandDelegate.execute(new UnequipItemCmd(character, dropped));
      character.getCarrying().setCarriedLeft(null);
    }
    else if (dropIndex.equals("R"))
    {
      dropped = character.getCarrying().getCarriedRight();
      CommandDelegate.execute(new UnequipItemCmd(character, dropped));
      character.getCarrying().setCarriedRight(null);
    }
    else 
    {
      List<Item> from;
      if (dropIndex.startsWith("W"))
      {
        from = character.getCarrying().getWorn();
        dropIndex = dropIndex.substring(1);
      }
      else
      {
        from = character.getCarrying().getCarriedInactive();
      }

      try
      {
        int index = Integer.parseInt(dropIndex);
        dropped = from.get(index);
        CommandDelegate.execute(new UnequipItemCmd(character, dropped));
        from.remove(index);
      }
      catch (NumberFormatException | IndexOutOfBoundsException e)
      {
        dropped = null;
      }
    }

    if (dropped != null)
    {
      character.getStats().incrementItemsDroppedCount();

      SendMessageCmd cmd = new SendMessageCmd(chatId, "You dropped " + dropped.getName());
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
}
