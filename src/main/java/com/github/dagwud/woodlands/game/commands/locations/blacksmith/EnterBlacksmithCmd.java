package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
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
    if (character.getParty().getBlacksmith().isBusyCrafting())
    {
      CommandDelegate.execute(new SendMessageCmd(character, "The door to the Blacksmith's shop is locked; you can hear the sounds of clashing steel coming from his workshop. He must be busy with something."));
      CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
    }

    Weapon collected = character.getParty().getBlacksmith().collectWeaponFor(character);
    if (null != collected)
    {
      CommandDelegate.execute(new SendMessageCmd(character, "The Blacksmith smiles as you enter. \"Ah!\" he says, \"I've got something for you. I think you're gonna like it \""));
      CommandDelegate.execute(new DoGiveItemCmd(character.getParty().getBlacksmith(), character, collected));
    }
  }
}
