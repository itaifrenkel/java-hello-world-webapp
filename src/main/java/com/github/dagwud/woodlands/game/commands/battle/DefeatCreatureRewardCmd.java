package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.SpawnItemCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.game.Settings;

public class DefeatCreatureRewardCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private static final int CHANCE_FARMED_ENCOUNTER = 20; // 1 in x
  private static final int CHANCE_NOT_FARMED_ENCOUNTER = 4; // 1 in x

  private final Party victoriousParty;
  private final Creature createdDefeated;
  private final boolean isFarmedEncounter;

  public DefeatCreatureRewardCmd(Party victoriousParty, Creature creatureDefeated, boolean isFarmedEncounter)
  {
    this.victoriousParty = victoriousParty;
    this.createdDefeated = creatureDefeated;
    this.isFarmedEncounter = isFarmedEncounter;
  }

  @Override
  public void execute()
  {
    int rollSpread = (isFarmedEncounter ? CHANCE_FARMED_ENCOUNTER : CHANCE_NOT_FARMED_ENCOUNTER);

    DiceRollCmd cmd = new DiceRollCmd(1, rollSpread);
    CommandDelegate.execute(cmd);
    if (cmd.getTotal() != rollSpread)
    {
      // Nothing this time
      return;
    }

    if (!victoriousParty.getCollectedItems().isEmpty())
    {
      CommandDelegate.execute(new SendPartyMessageCmd(victoriousParty, "Your party has unclaimed items"));
      if (victoriousParty.getCollectedItems().size() >= Settings.MAX_UNCLAIMED_PARTY_ITEMS)
      {
        return;
      }
    }

    SpawnItemCmd itemDrop = new SpawnItemCmd(true);
    CommandDelegate.execute(itemDrop);
    Item dropped = itemDrop.getSpawned();
    victoriousParty.getCollectedItems().add(dropped);
    CommandDelegate.execute(new SendPartyMessageCmd(victoriousParty,
            "<b>" + createdDefeated.name + " dropped a " + dropped.getName() + "!</b>"));
  }
}
