package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

class WardOfAlacrity extends Trinket
{
  private static final long serialVersionUID = 1L;
  private static final int BOOST_AMOUNT = 1;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().getAgility().addBonus(BOOST_AMOUNT);

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
    fighter.getStats().getAgility().removeBonus(BOOST_AMOUNT);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), produceUnequipMessage()));
    }
  }

  String produceEquipMessage(Fighter fighter)
  {
    return "The ward makes you feel vibrant and energetic. You feel a sense of purpose and a swiftness of body. Your agility is now " + fighter.getStats().getAgility();
  }

  @Override
  public String getName()
  {
    return "Ward of Alacrity";
  }

  @Override
  public String getIcon()
  {
    return WARD_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "\uD83C\uDFC3" + BOOST_AMOUNT;
  }
}
