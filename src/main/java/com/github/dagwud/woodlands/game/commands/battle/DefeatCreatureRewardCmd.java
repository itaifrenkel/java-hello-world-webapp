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

public class DefeatCreatureRewardCmd extends AbstractCmd
{
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
      return;
    }

    DiceRollCmd cmd = new DiceRollCmd(1, 20);
    CommandDelegate.execute(cmd);
    if (cmd.getTotal() != 20)
    {
      // Nothing this time
      return;
    }

    SpawnItemCmd itemDrop = new SpawnItemCmd(true);
    CommandDelegate.execute(itemDrop);
    Item dropped = itemDrop.getSpawned();
    victoriousParty.getCollectedItems().add(dropped);
    new SendPartyMessageCmd(victoriousParty,
            createdDefeated.name + " dropped " + dropped.getName() + "!").go();
  }
}
