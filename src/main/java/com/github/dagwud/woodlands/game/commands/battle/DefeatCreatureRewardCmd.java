package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.SpawnItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.SpawnTrinketCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Settings;

public class DefeatCreatureRewardCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Party victoriousParty;
  private final Creature createdDefeated;

  public DefeatCreatureRewardCmd(Party victoriousParty, Creature creatureDefeated)
  {
    this.victoriousParty = victoriousParty;
    this.createdDefeated = creatureDefeated;
  }

  @Override
  public void execute()
  {
    if (!victoriousParty.getCollectedItems().isEmpty())
    {
      new SendPartyMessageCmd(victoriousParty, "Your party has unclaimed items").go();
      if (victoriousParty.getCollectedItems().size() >= Settings.MAX_UNCLAIMED_PARTY_ITEMS)
      {
        return;
      }!

    }

    DiceRollCmd cmd = new DiceRollCmd(1, 6);
    CommandDelegate.execute(cmd);
    if (cmd.getTotal() != 6)
    {
      // Nothing this time
      return;
    }

    SpawnItemCmd itemDrop = new SpawnItemCmd(true);
    CommandDelegate.execute(itemDrop);
    Item dropped = itemDrop.getSpawned();
    victoriousParty.getCollectedItems().add(dropped);
    new SendPartyMessageCmd(victoriousParty,
            "<b>" + createdDefeated.name + " dropped a " + dropped.getName() + "!</b>").go();
  }
}
