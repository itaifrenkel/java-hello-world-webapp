package com.github.dagwud.woodlands.game.commands.locations.alchemist;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;
import com.github.dagwud.woodlands.game.items.EquippableItem;

public class EnterAlchemistCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter character;

  public EnterAlchemistCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    EquippableItem collected = character.getParty().getAlchemist().collectFor(character);

    if (null != collected)
    {
      String msg;
      if (character.getParty().getAlchemist().isBusyCrafting())
      {
        msg = "The Alchemist opens the jar a crack and peeks through. A foul burning smell wafts through from inside his store. \"You have poor timing,\" he says. \"I'm in the middle of something rather delicate. Here, take this.\" \nHe passes you a bundle through the door, and before you can reply he shuts it in your face.";
      }
      else
      {
        msg = "As your reach up to knock, the door swings open as if by magic... or perhaps it has something to do with the string attached to the door that loops across the roof and drops down behind the counter. The Alchemist looks smug. \"It's a fine piece of work, even if I do say so myself,\" he says, handing you a package wrapped in brown paper, covered in what looks (but doesn't smell) like grease.";
      }
      CommandDelegate.execute(new SendMessageCmd(character, msg));
      CommandDelegate.execute(new SendAdminMessageCmd(character.getName() + " is collecting " + collected.getName() + " from the Alchemist"));
      CommandDelegate.execute(new DoGiveItemCmd(character.getParty().getAlchemist(), character, collected));
    }
    else
    {
      if (character.getParty().getAlchemist().isBusyCrafting())
      {
        int remainingMinutes = character.getParty().getAlchemist().determineRemainingCraftingMinutes();
        CommandDelegate.execute(new SendMessageCmd(character, "The door to the Alchemist's store is locked; and judging by the smell lingering around the building, " +
                   "perhaps you're better off out here in the fresh air. In response to your knocking, a spell activates on his door and a burn mark appears, " +
                   "showing the words \"COME BACK IN " + remainingMinutes + "\". He must be busy with something, so you resolve to return later."));
      }
    }

    if (character.getParty().getAlchemist().isBusyCrafting())
    {
      CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
    }
  }
}
