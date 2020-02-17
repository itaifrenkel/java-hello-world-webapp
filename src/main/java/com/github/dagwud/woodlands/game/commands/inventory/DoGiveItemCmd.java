package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;

public class DoGiveItemCmd extends AbstractCmd
{
  private final GameCharacter giver;
  private final List<Item> from;
  private final GameCharacter receiver;
  private final Item itemToGive;

  public DoGiveItemCmd(GameCharacter giver, List<Item> from, GameCharacter receiver, Item itemToGive)
  {
    this.from = from;
    this.giver = giver;
    this.receiver = receiver;
    this.itemToGive = itemToGive;
  }

  @Override
  public void execute() throws Exception
  {
    from.remove(itemToGive);
    receiver.getCarrying().getCarriedInactive().add(itemToGive);

    CommandDelegate.execute(new UnequipItemCmd(giver, itemToGive));
    if (giver instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendMessageCmd(((PlayerCharacter)giver).getPlayedBy().getChatId(), "You give the " + itemToGive.getName() + " to " + receiver.getName()));
    }
    if (receiver instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendMessageCmd(((PlayerCharacter)receiver).getPlayedBy().getChatId(),
              giver.getName() + " gave you a " + itemToGive.getName() + " - what a sweetie."));
    }
  }
}
