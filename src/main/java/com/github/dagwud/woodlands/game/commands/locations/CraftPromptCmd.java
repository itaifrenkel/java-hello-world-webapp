package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public abstract class CraftPromptCmd<A extends Item, B extends Item> extends SuspendableCmd
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
        if (null != secondItem)
        {
          schedule();
        }
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
    ChoiceCmd choice = new ChoiceCmd(getCharacter().getPlayedBy().getChatId(),
            produceFirstItemPromptMessage(), produceFirstItemOptions());
    CommandDelegate.execute(choice);
  }

  protected abstract String produceFirstItemPromptMessage();

  protected abstract String[] produceFirstItemOptions();

  protected abstract A findFirstItem(String name);

  protected abstract String produceAcceptItemMessage();

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

    CommandDelegate.execute(new SendMessageCmd(getCharacter(), produceAcceptedJobMessage()));
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

  private void schedule()
  {
    CommandDelegate.execute(new DoGiveItemCmd(character, character.getParty().getBlacksmith(), firstItem));
    CommandDelegate.execute(new DoGiveItemCmd(character, character.getParty().getBlacksmith(), secondItem));
    CommandDelegate.execute(createCraftCmd(firstItem, secondItem));
    CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
  }

  protected abstract AbstractCmd createCraftCmd(A firstItem, B secondItem);

  public PlayerCharacter getCharacter()
  {
    return character;
  }
}
