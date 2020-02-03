package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

public class RetrieveItemsCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public RetrieveItemsCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int maxAllowedItems = character.determineMaxAllowedItems();
    if (character.getCarrying().countTotalCarried() >= maxAllowedItems)
    {
      CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "You can't carry any more"));
      return;
    }

    Item chosenItem = chooseItem();

    character.getCarrying().getCarriedInactive().add(chosenItem);
    CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "You pick up a " + chosenItem.getName() + " " + chosenItem.getIcon() + chosenItem.statsSummary(character)));
  }

  private Item chooseItem()
  {
    Item chosenItem = null;
    while (chosenItem == null)
    {
      List<Item> allItems = ItemsCacheFactory.instance().getCache().getAllItems();
      int rand = (int) (Math.random() * allItems.size());
      chosenItem = allItems.get(rand);
      if (chosenItem.preventSpawning)
      {
        chosenItem = null; // try again
      }
    }
    return chosenItem;
  }

}
