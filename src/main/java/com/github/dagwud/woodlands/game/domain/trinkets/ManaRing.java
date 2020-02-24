package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverManaCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ManaRing extends TimedBenefitWearableTrinket
{
  private static final long serialVersionUID = 1L;
  private static final int MANA_REGEN = 1;
  private static final long REGENRATE_EVERY_MS = 30 * 60_000; // 30 minutes

  @Override
  public void applyBenefit(Fighter applyTo)
  {
    RecoverManaCmd mana = new RecoverManaCmd(applyTo, 1);
    CommandDelegate.execute(mana);
    if (applyTo instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) applyTo;
      int recovered = mana.getManaRecovered();
      if (recovered != 0)
      {
        new SendMessageCmd(character.getPlayedBy().getChatId(), "You recover " + recovered + "✨ through your " + getName()).go();
      }
    }
  }

  @Override
  public long getBenefitRepeatDurationMS()
  {
    return REGENRATE_EVERY_MS;
  }

  @Override
  String produceEquipMessage(Fighter fighter)
  {
    return "The ring gives a slight pulse as you put it on.";
  }

  @Override
  public String getName()
  {
    return "Ring of Mana Regeneration";
  }

  @Override
  public String getIcon()
  {
    return WARD_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "✨" + MANA_REGEN + "/" + (REGENRATE_EVERY_MS / 60_000) + "m";
  }
}
