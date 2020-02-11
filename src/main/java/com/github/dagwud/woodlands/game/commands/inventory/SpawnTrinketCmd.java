package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;
import com.github.dagwud.woodlands.game.domain.trinkets.TrinketFactory;

public class SpawnTrinketCmd extends AbstractCmd
{
  private final Fighter receiver;

  public SpawnTrinketCmd(Fighter receiver)
  {
    super((CommandPrerequisite) receiver::canCarryMore);
    this.receiver = receiver;
  }

  @Override
  public void execute()
  {
    if (!receiver.canCarryMore())
    {
      if (receiver instanceof PlayerCharacter)
      {
        PlayerCharacter p = (PlayerCharacter)receiver;
        CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), "You can't carry any more"));
      }
      return;
    }
    Trinket trinket = TrinketFactory.instance().create();
    receiver.getCarrying().getCarriedInactive().add(trinket);
  }
}
