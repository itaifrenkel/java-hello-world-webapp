package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.GiveItemCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;

public class CraftWeaponPromptCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private Weapon firstWeapon;
  private Weapon secondWeapon;

  public CraftWeaponPromptCmd(PlayerCharacter character, PlayerState playerState)
  {
    super(playerState, 3);
    this.character = character;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForFirstWeapon();
        return;
      case 1:
        acceptFirstWeaponAndPromptForSecondWeapon(capturedInput);
        return;
      case 2:
        acceptSecondWeaponAndSchedule(capturedInput);
    }
  }

  private void promptForFirstWeapon()
  {
    ChoiceCmd choice = new ChoiceCmd(character.getPlayedBy().getChatId(),
            "\"What've you got for me to work with?\"", produceWeaponOptions());
    CommandDelegate.execute(choice);
  }

  private void acceptFirstWeaponAndPromptForSecondWeapon(String capturedInput)
  {
    Weapon weapon = findWeapon(capturedInput);
    if (null == weapon)
    {
      CommandDelegate.execute(new SendMessageCmd(character, "I can't work with that. What've you got that's worth something?"));
      rejectCapturedInput();
      return;
    }
    firstWeapon = weapon;

    CommandDelegate.execute(new SendMessageCmd(character, "\"Yeah, I can work with that. What else you got?\""));
    ChoiceCmd choice = new ChoiceCmd(character.getPlayedBy().getChatId(),
            "\"What else you got?\"", produceWeaponOptions(capturedInput));
    CommandDelegate.execute(choice);
  }

  private void acceptSecondWeaponAndSchedule(String capturedInput)
  {
    Weapon weapon = findWeapon(capturedInput);
    if (null == weapon)
    {
      CommandDelegate.execute(new SendMessageCmd(character, "I can't work with that. What've you got that's worth something?"));
      rejectCapturedInput();
      return;
    }
    secondWeapon = weapon;

    CommandDelegate.execute(new SendMessageCmd(character, "\"I'll see what I can do. Come back in a few hours.\""));
    CommandDelegate.execute(new DoGiveItemCmd(character, character.getParty().getBlacksmith(), firstWeapon));
    CommandDelegate.execute(new DoGiveItemCmd(character, character.getParty().getBlacksmith(), secondWeapon));
    CommandDelegate.execute(new StartWeaponCraft(character.getParty().getBlacksmith(), character, firstWeapon, secondWeapon));
    CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
  }

  private Weapon findWeapon(String name)
  {
    if (character.getCarrying().getCarriedLeft() != null && character.getCarrying().getCarriedLeft().getName().equals(name))
    {
      return (Weapon) character.getCarrying().getCarriedLeft();
    }
    if (character.getCarrying().getCarriedRight() != null && character.getCarrying().getCarriedRight().getName().equals(name))
    {
      return (Weapon) character.getCarrying().getCarriedRight();
    }
    for (Item item : character.getCarrying().getCarriedInactive())
    {
      if (item instanceof Weapon && item.getName().equals(name))
      {
        return (Weapon) item;
      }
    }
    return null;
  }

  private String[] produceWeaponOptions()
  {
    return produceWeapons().toArray(new String[0]);
  }

  private String[] produceWeaponOptions(String exclude)
  {
    List<String> weapons = produceWeapons();
    weapons.remove(exclude);
    return weapons.toArray(new String[0]);
  }

  private List<String> produceWeapons()
  {
    List<String> weapons = new ArrayList<>();
    if (character.getCarrying().getCarriedLeft() != null && character.getCarrying().getCarriedLeft() instanceof Weapon)
    {
      weapons.add(character.getCarrying().getCarriedLeft().getName());
    }
    if (character.getCarrying().getCarriedRight() != null && character.getCarrying().getCarriedRight() instanceof Weapon)
    {
      weapons.add(character.getCarrying().getCarriedRight().getName());
    }
    for (Item inactive : character.getCarrying().getCarriedInactive())
    {
      if (inactive instanceof Weapon)
      {
        weapons.add(inactive.getName());
      }
    }
    return weapons;
  }
}
