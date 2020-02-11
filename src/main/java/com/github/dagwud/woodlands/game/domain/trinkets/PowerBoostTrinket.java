package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

abstract class PowerBoostTrinket extends Trinket
{
  private static final long serialVersionUID = 1L;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().getMaxHitPoints().addBonus(getBoostAmount());
    RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(fighter, getBoostAmount());
    CommandDelegate.execute(cmd);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      String msg = produceEquipMessage(fighter);
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), msg));
    }
  }

  @Override
  public void unequip(Fighter fighter)
  {
    fighter.getStats().getMaxHitPoints().removeBonus(getBoostAmount());
    ReduceHitPointsCmd cmd = new ReduceHitPointsCmd(fighter, 0); // causes a check that you're not over your maximum
    CommandDelegate.execute(cmd);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), produceUnequipMessage()));
    }
  }

  abstract int getBoostAmount();

  abstract String produceEquipMessage(Fighter fighter);
}
