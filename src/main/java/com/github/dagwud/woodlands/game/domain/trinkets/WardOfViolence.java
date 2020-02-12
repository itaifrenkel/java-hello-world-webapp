package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

class WardOfViolence extends WearableTrinket
{
  private static final long serialVersionUID = 1L;
  private static final int BOOST_AMOUNT = 10;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().setBonusDamage(fighter.getStats().getBonusDamage() + BOOST_AMOUNT);

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
    fighter.getStats().setBonusDamage(fighter.getStats().getBonusDamage() - BOOST_AMOUNT);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), produceUnequipMessage()));
    }
  }

  String produceEquipMessage(Fighter fighter)
  {
    return "A dark feeling overcomes you; you feel malice spreading through your body - and it feels good. You feel powerful and dangerous.";
  }

  @Override
  public String getName()
  {
    return "Ward of Violence";
  }

  @Override
  public String getIcon()
  {
    return WARD_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "⚔️🏹 +" + BOOST_AMOUNT;
  }
}
