package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

import java.util.ArrayList;
import java.util.List;

public class ClaimItemCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final Player player;

  public ClaimItemCmd(Player player)
  {
    super(player.getPlayerState(), 2);
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

    if (!player.getActiveCharacter().canCarryMore())
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "You already have enough stuff; stop being greedy.");
      CommandDelegate.execute(cmd);
      return;
    }

    player.getActiveCharacter().getParty().getCollectedItems().remove(claimed);
    player.getActiveCharacter().getCarrying().getCarriedInactive().add(claimed);

    String msg = "<b>" + player.getActiveCharacter().getName() +
        " has claimed " + claimed.summary(player.getActiveCharacter()) + "</b>";
    SendPartyMessageCmd cmd = new SendPartyMessageCmd(player.getActiveCharacter().getParty(), msg);
    CommandDelegate.execute(cmd);
  }

  private Item findItem(String capturedInput)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();

    for (Item item : activeCharacter.getParty().getCollectedItems())
    {
      if (item.getName().equals(capturedInput))
      {
        return item;
      }
    }
    return null;
  }

  private String[] buildItemsList(Player player)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();
    List<String> unclaimedItems = new ArrayList<>()
    for (Item i : activeCharacter.getParty().getCollectedItems())
    {
      unclaimedItems.add(i.getName());
    }
    unclaimedItems.add("Cancel");

    return unclaimedItems.toArray(new String[0]);
  }
}
