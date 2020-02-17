package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;
import com.github.dagwud.woodlands.game.domain.trinkets.TrinketFactory;
import com.github.dagwud.woodlands.game.items.EquippableItem;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;

import java.util.List;

public class SpawnItemCmd extends AbstractCmd
{
  private final boolean trinketsAllowed;
  private Item spawned;

  public SpawnItemCmd(boolean trinketsAllowed)
  {
    this.trinketsAllowed = trinketsAllowed;
  }

  @Override
  public void execute()
  {
    if (trinketsAllowed && shouldSpawnTrinket())
    {
      spawned = spawnTrinket();
      return;
    }
    spawned = spawnEquippableItem();
  }

  private boolean shouldSpawnTrinket()
  {
    DiceRollCmd cmd = new DiceRollCmd(1, 6);
    CommandDelegate.execute(cmd);
    return cmd.getTotal() == 6;
  }

  private Trinket spawnTrinket()
  {
    return TrinketFactory.instance().create();
  }

  private EquippableItem spawnEquippableItem()
  {
    EquippableItem chosenItem = null;
    while (chosenItem == null)
    {
      List<EquippableItem> allItems = ItemsCacheFactory.instance().getCache().getAllItems();
      int rand = (int) (Math.random() * allItems.size());
      chosenItem = allItems.get(rand);
      if (chosenItem.preventSpawning)
      {
        chosenItem = null; // try again
      }
    }
    return chosenItem;
  }

  public Item getSpawned()
  {
    return spawned;
  }
}
