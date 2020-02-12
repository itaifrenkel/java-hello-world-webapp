package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

abstract class DefenceBoostTrinket extends WearableTrinket
{
  private static final long serialVersionUID = 1L;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().setDefenceRatingBoost(fighter.getStats().getDefenceRatingBoost() + getBoostAmount());

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
    fighter.getStats().setDefenceRatingBoost(fighter.getStats().getDefenceRatingBoost() - getBoostAmount());

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), produceUnequipMessage()));
    }
  }

  abstract int getBoostAmount();

  abstract String produceEquipMessage(Fighter fighter);

}
