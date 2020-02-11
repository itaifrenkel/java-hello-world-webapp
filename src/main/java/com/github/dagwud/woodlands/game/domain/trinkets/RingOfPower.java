package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class RingOfPower extends Trinket
{
  private static final int MAX_HP_BOOST = 4;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().getMaxHitPoints().addBonus(MAX_HP_BOOST);
    RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(fighter, MAX_HP_BOOST);
    CommandDelegate.execute(cmd);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      String msg = "You feel a rush of power as you put on the ring. Your maximum HP has been boosted to ❤" + fighter.getStats().getMaxHitPoints().total();
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), msg));
    }
  }

  @Override
  public void unequip(Fighter fighter)
  {
    fighter.getStats().getMaxHitPoints().removeBonus(MAX_HP_BOOST);
    ReduceHitPointsCmd cmd = new ReduceHitPointsCmd(fighter, 0); // causes a check that you're not over your maximum
    CommandDelegate.execute(cmd);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), "You no longer feel the effects of the Ring of Power"));
    }
  }

  @Override
  public String getName()
  {
    return "Ring of Power";
  }

  @Override
  public String getIcon()
  {
    return RING_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "❤" + MAX_HP_BOOST;
  }
}
