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
  private static final long serialVersionUID = 1L;

  private final Fighter receiver;
  private final Trinket trinket;

  public SpawnTrinketCmd(Fighter receiver)
  {
    this(receiver, TrinketFactory.instance().create());
  }

  public SpawnTrinketCmd(Fighter receiver, Trinket trinket)
  {
    super((CommandPrerequisite) receiver::canCarryMore);
    this.receiver = receiver;
    this.trinket = trinket;
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
    receiver.getCarrying().getCarriedInactive().add(trinket);

    if (receiver instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter)receiver;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), "You received a " + trinket.summary(receiver)));
    }
  }
}
