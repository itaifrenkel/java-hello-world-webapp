package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.InventoryCmd;
import com.github.dagwud.woodlands.game.domain.npc.Crafter;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @param <A> first input item type
 * @param <B> second input item type
 * @param <C> resulting item type
 */
public abstract class CraftPromptCmd<A extends Item, B extends Item, C extends Item> extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private A firstItem;
  private B secondItem;

  public CraftPromptCmd(PlayerCharacter character, PlayerState playerState)
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
        firstItem = acceptFirstWeaponAndPromptForSecondWeapon(capturedInput);
        return;
      case 2:
        secondItem = acceptSecondItem(capturedInput);
        if (null == secondItem)
        {
          return;
        }

        C toCraft = createCraftedItem(firstItem, secondItem);
        if (toCraft == null)
        {
          CommandDelegate.execute(new SendMessageCmd(character, produceJobDeclinedMessage()));
          return;
        }
        CommandDelegate.execute(new SendMessageCmd(getCharacter(), produceAcceptedJobMessage()));
        schedule(toCraft);
    }
  }

  private A acceptFirstWeaponAndPromptForSecondWeapon(String capturedInput)
  {
    if (capturedInput.equals("Cancel"))
    {
      removeSuspendable();
      return null;
    }

    A firstItem = findFirstItem(capturedInput);
    if (null == firstItem)
    {
      CommandDelegate.execute(new SendMessageCmd(getCharacter(), produceCantWorkWithMessage()));
      rejectCapturedInput();
      return null;
    }

    CommandDelegate.execute(new SendMessageCmd(getCharacter(), produceAcceptItemMessage()));
    ChoiceCmd choice = new ChoiceCmd(getCharacter().getPlayedBy().getChatId(),
            produceSecondItemPromptMessage(), produceSecondItemOptions(capturedInput));
    CommandDelegate.execute(choice);
    return firstItem;
  }

  private void promptForFirstWeapon()
  {
    CommandDelegate.execute(new InventoryCmd(getPlayerState().getPlayer().getChatId(), getCharacter()));
    ChoiceCmd choice = new ChoiceCmd(getCharacter().getPlayedBy().getChatId(),
            produceFirstItemPromptMessage(), produceFirstItemOptions());
    CommandDelegate.execute(choice);
  }

  protected abstract String produceFirstItemPromptMessage();

  protected abstract String[] produceFirstItemOptions();

  protected abstract A findFirstItem(String name);

  protected abstract String produceAcceptItemMessage();

  protected abstract C createCraftedItem(A firstItem, B secondItem);

  protected abstract String produceCantWorkWithMessage();

  protected abstract String produceSecondItemPromptMessage();

  protected abstract String[] produceSecondItemOptions(String exclude);

  private B acceptSecondItem(String capturedInput)
  {
    if (capturedInput.equals("Cancel"))
    {
      removeSuspendable();
      return null;
    }

    B item = findSecondItem(capturedInput);
    if (null == item)
    {
      CommandDelegate.execute(new SendMessageCmd(getCharacter(), produceCantWorkWithMessage()));
      rejectCapturedInput();
      return null;
    }

    return item;
  }

  protected abstract B findSecondItem(String name);

  protected abstract String produceAcceptedJobMessage();

  protected Item findItem(String name)
  {
    if (getCharacter().getCarrying().getCarriedLeft() != null && getCharacter().getCarrying().getCarriedLeft().getName().equals(name))
    {
      return getCharacter().getCarrying().getCarriedLeft();
    }
    if (getCharacter().getCarrying().getCarriedRight() != null && getCharacter().getCarrying().getCarriedRight().getName().equals(name))
    {
      return getCharacter().getCarrying().getCarriedRight();
    }
    for (Item item : getCharacter().getCarrying().getCarriedInactive())
    {
      if (item.getName().equals(name))
      {
        return item;
      }
    }
    return null;
  }

  private void schedule(C toCraft)
  {
    CommandDelegate.execute(new DoGiveItemCmd(character, getCrafter(), firstItem));
    CommandDelegate.execute(new DoGiveItemCmd(character,getCrafter(), secondItem));
    CommandDelegate.execute(createCraftCmd(toCraft));
    CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
  }

  protected abstract String produceJobDeclinedMessage();

  protected abstract AbstractCmd createCraftCmd(C crafted);

  protected abstract Crafter getCrafter();

  public PlayerCharacter getCharacter()
  {
    return character;
  }


  protected final List<String> produceWeapons()
  {
    List<String> weapons = new ArrayList<>();

    if (getCharacter().getCarrying().getCarriedLeft() != null && getCharacter().getCarrying().getCarriedLeft() instanceof Weapon)
    {
      Weapon left = (Weapon) getCharacter().getCarrying().getCarriedLeft();
      if (left.damage.determineAverageRollAmount() < Settings.MAX_CRAFTABLE_WEAPON_DAMAGE)
      {
        weapons.add(getCharacter().getCarrying().getCarriedLeft().getName());
      }
    }

    if (getCharacter().getCarrying().getCarriedRight() != null && getCharacter().getCarrying().getCarriedRight() instanceof Weapon)
    {
      Weapon right = (Weapon)getCharacter().getCarrying().getCarriedRight();
      if (right.damage.determineAverageRollAmount() < Settings.MAX_CRAFTABLE_WEAPON_DAMAGE)
      {
        weapons.add(getCharacter().getCarrying().getCarriedRight().getName());
      }
    }

    for (Item inactive : getCharacter().getCarrying().getCarriedInactive())
    {
      if (inactive instanceof Weapon)
      {
        if (((Weapon)inactive).damage.determineAverageRollAmount() < Settings.MAX_CRAFTABLE_WEAPON_DAMAGE)
        {
          weapons.add(inactive.getName());
        }
      }
    }

    weapons.add("Cancel");
    return weapons;
  }

  protected final List<String> producePotions()
  {
    List<String> collect = getCharacter().produceItems(ConsumableTrinket.class)
            .stream()
            .map(Trinket::getName)
            .collect(Collectors.toList());

    collect.add("Cancel");

    return collect;
  }
}
