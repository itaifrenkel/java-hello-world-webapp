package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RollShortRestCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class HealingWard extends TimedBenefitWearableTrinket
{
  private static final long serialVersionUID = 1L;
  private static final BigDecimal HP_REGEN_PERCENT_OF_SHORT_REST = new BigDecimal("0.2");
  private static final long REGENRATE_EVERY_MS = 45 * 60_000; // 45 minutes

  @Override
  public void applyBenefit(Fighter applyTo)
  {
    RollShortRestCmd roll = new RollShortRestCmd((GameCharacter) applyTo, HP_REGEN_PERCENT_OF_SHORT_REST);
    CommandDelegate.execute(roll);

    int hpRecovered = Math.max(1, roll.getRecoveredHitPoints());

    RecoverHitPointsCmd recover = new RecoverHitPointsCmd(applyTo, hpRecovered);
    CommandDelegate.execute(recover);
    hpRecovered = recover.getHitPointsRecovered();
    if (applyTo instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) applyTo;
      if (hpRecoverd != 0)
      {
        CommandDelegate.execute(new SendMessageCmd(character, "You recover " + hpRecovered + "❤ through your " + getName()));
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
    return "The ward takes up a position near your shoulder, casting a faint pulsing light as it bobs along just behind you.";
  }

  @Override
  public String getName()
  {
    return "Healing Ward";
  }

  @Override
  public String getIcon()
  {
    return WARD_ICON;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "❤" + HP_REGEN_PERCENT_OF_SHORT_REST.toPlainString() + "% of " + EState.SHORT_RESTING.icon + "/" + (REGENRATE_EVERY_MS / 60_000) + "m";
  }
}
