package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.ApplyTimeBasedBenefitCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public abstract class TimedBenefitWearableTrinket extends WearableTrinket
{
  private static final long serialVersionUID = 1L;

  public abstract void applyBenefit(Fighter applyTo);

  public abstract long getBenefitRepeatDurationMS();

  @Override
  public void equip(Fighter fighter)
  {
    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      String msg = produceEquipMessage(fighter);
      CommandDelegate.execute(new SendMessageCmd(p, msg));
    }

    ApplyTimeBasedBenefitCmd apply = new ApplyTimeBasedBenefitCmd(fighter, this);
    CommandDelegate.execute(new RunLaterCmd(getBenefitRepeatDurationMS(), apply));
  }

  @Override
  public final void unequip(Fighter fighter)
  {
    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter p = (PlayerCharacter) fighter;
      CommandDelegate.execute(new SendMessageCmd(p, produceUnequipMessage()));
    }
  }

  abstract String produceEquipMessage(Fighter fighter);

}
