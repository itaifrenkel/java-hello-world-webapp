package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.GiveItemCmd;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class RetrieveItemsCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  public RetrieveItemsCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2, new AbleToActPrerequisite(character));
  }

  @Override
  public void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        String[] items = buildItemsList(getPlayerState().getActiveCharacter().getInnkeeper().getCarrying());
        ChoiceCmd cmd = new ChoiceCmd(getPlayerState().getPlayer().getChatId(), "What would you like to retrieve?", items);
        CommandDelegate.execute(cmd);
        return;
      case 1:
        Item toRetrieve = findItem(capturedInput);
        if (toRetrieve == null)
        {
          CommandDelegate.execute(new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "\"Suit yourself\""));
          return;
        }
        retrieveItem(toRetrieve);
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "\"Ah, I thought you might be needing that\""));
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
    for (Item item : getPlayerState().getPlayer().getActiveCharacter().getInnkeeper().getCarrying().getCarriedInactive())
    {
      if (item.getName().equalsIgnoreCase(capturedInput))
      {
        return item;
      }
    }
    return null;
  }

  private void retrieveItem(Item toRetrieve)
  {
    PlayerCharacter character = getPlayerState().getPlayer().getActiveCharacter();
    DoGiveItemCmd give = new DoGiveItemCmd(character.getInnkeeper(),
            character.getInnkeeper().getCarrying().getCarriedInactive(),
            character, toRetrieve);
    CommandDelegate.execute(give);
  }
}
