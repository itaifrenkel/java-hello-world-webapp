package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

class CloakOfShadows extends WearableTrinket
{
  private static final long serialVersionUID = 1L;
  private static final int BOOST_AMOUNT = 2;

  @Override
  public void equip(Fighter fighter)
  {
    fighter.getStats().setDefenceRatingBoost(fighter.getStats().getDefenceRatingBoost() + BOOST_AMOUNT);

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
    fighter.getStats().setDefenceRatingBoost(fighter.getStats().getDefenceRatingBoost() - BOOST_AMOUNT);

    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p.getPlayedBy().getChatId(), produceUnequipMessage()));
    }
  }

  String produceEquipMessage(Fighter fighter)
  {
    return "Donning the cloak doesn't make you feel any different, but when you look at your arms they seem blurry and hard to focus on. That'll definitely make you harder to hit.";
  }

  @Override
  public String getName()
  {
    return "Cloak of Shadows";
  }

  @Override
  public String getIcon()
  {
    return WARD_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "🛡+" + BOOST_AMOUNT;
  }
}
