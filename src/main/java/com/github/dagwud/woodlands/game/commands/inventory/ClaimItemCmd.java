package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.events.CharacterItemEvent;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

import java.util.ArrayList;
import java.util.List;

public class ClaimItemCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final Player player;

  public ClaimItemCmd(Player player)
  {
    super(player.getPlayerState(), 2, new AbleToActPrerequisite(player.getActiveCharacter()));
    this.player = player;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        String[] items = buildItemsList(getPlayerState().getPlayer());
        ChoiceCmd choice = new ChoiceCmd(player.getChatId(), "To which spoils of war do you lay claim?", items);
        CommandDelegate.execute(choice);
        break;
      case 1:
        claimItem(capturedInput);
        break;
    }
  }

  private void claimItem(String capturedInput)
  {
    if (capturedInput.equals("Cancel"))
    {
      return;
    }

    Item claimed = findItem(capturedInput);
    if (claimed == null)
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "You can't claim that.");
      CommandDelegate.execute(cmd);
      return;
    }

    PlayerCharacter activeCharacter = player.getActiveCharacter();

    if (!activeCharacter.canCarryMore())
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "You already have enough stuff; stop being greedy.");
      CommandDelegate.execute(cmd);
      return;
    }

    activeCharacter.getParty().getCollectedItems().remove(claimed);
    activeCharacter.getCarrying().getCarriedInactive().add(claimed);

    EEvent.CLAIMED_ITEM.trigger(new CharacterItemEvent(activeCharacter, claimed));
  }

  private Item findItem(String capturedInput)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();

    for (Item item : activeCharacter.getParty().getCollectedItems())
    {
      if (item.getName().equalsIgnoreCase(capturedInput))
      {
        return item;
      }
    }
    return null;
  }

  private String[] buildItemsList(Player player)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();
    List<String> unclaimedItems = new ArrayList<>();
    for (Item i : activeCharacter.getParty().getCollectedItems())
    {
      unclaimedItems.add(i.getName());
    }
    unclaimedItems.add("Cancel");

    return unclaimedItems.toArray(new String[0]);
  }
}
