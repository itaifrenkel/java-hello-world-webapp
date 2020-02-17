package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.GiveItemCmd;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class LeaveItemsCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  public LeaveItemsCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2);
  }

  @Override
  public void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        String[] items = buildItemsList(getPlayerState().getActiveCharacter().getCarrying());
        ChoiceCmd cmd = new ChoiceCmd(getPlayerState().getPlayer().getChatId(), "What would you like to leave with the Innkeeper?", items);
        CommandDelegate.execute(cmd);
        return;
      case 1:
        Item toLeave = findItem(capturedInput);
        if (toLeave == null)
        {
          CommandDelegate.execute(new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "\"Suit yourself\""));
          return;
        }
        leaveItem(toLeave);
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "\"Sure, I'll hang on to that for you\""));
    }
  }

  private String[] buildItemsList(CarriedItems carrying)
  {
    List<String> items = new ArrayList<>();
    for (Item item : carrying.getCarriedInactive())
    {
      items.add(item.getName());
    }
    return items.toArray(new String[0]);
  }

  private Item findItem(String capturedInput)
  {
    for (Item item : getPlayerState().getPlayer().getActiveCharacter().getCarrying().getCarriedInactive())
    {
      if (item.getName().equalsIgnoreCase(capturedInput))
      {
        return item;
      }
    }
    return null;
  }

  private void leaveItem(Item toLeave)
  {
    PlayerCharacter giver = getPlayerState().getPlayer().getActiveCharacter();
    DoGiveItemCmd give = new DoGiveItemCmd(giver,
            giver.getCarrying().getCarriedInactive(),
            giver.getInnkeeper(), toLeave);
    CommandDelegate.execute(give);
  }
}
