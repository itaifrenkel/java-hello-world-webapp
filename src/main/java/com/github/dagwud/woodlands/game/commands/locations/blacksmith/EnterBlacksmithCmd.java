package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class EnterBlacksmithCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter character;

  public EnterBlacksmithCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Weapon collected = character.getParty().getBlacksmith().collectFor(character);

    if (null != collected)
    {
      String msg;
      if (character.getParty().getBlacksmith().isBusyCrafting())
      {
        msg = "The sounds of metal clashing stop when you rap the heavy iron door-knocker. After a brief pause, the Blacksmith opens the door. \"Aha,\" he says, wiping sweat from his forehead with a rag. \"It's ready. I'm quite proud of this piece. Now if you'll excuse me, I've got another commission in the furnace.\"";
      }
      else
      {
        msg = "The Blacksmith looks up and smiles as you enter. \"Ah!\" he says, \"I've got something for you. I think you're gonna like it \"";
      }
      CommandDelegate.execute(new SendMessageCmd(character, msg));
      CommandDelegate.execute(new SendAdminMessageCmd(character.getName() + " is collecting " + collected.getName() + " from the Blacksmith"));
      CommandDelegate.execute(new DoGiveItemCmd(character.getParty().getBlacksmith(), character, collected));
    }
    else
    {
      if (character.getParty().getBlacksmith().isBusyCrafting())
      {
        CommandDelegate.execute(new SendMessageCmd(character, "The door to the Blacksmith's shop is locked; you can hear the sounds of clashing steel coming from his workshop. He must be busy with something."));
      }
    }

    if (character.getParty().getBlacksmith().isBusyCrafting())
    {
      CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
    }
  }
}
