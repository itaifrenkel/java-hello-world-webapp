package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.trinkets.TimedBenefitWearableTrinket;

public class ApplyTimeBasedBenefitCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final TimedBenefitWearableTrinket trinket;
  private final Fighter wearer;

  public ApplyTimeBasedBenefitCmd(Fighter wearer, TimedBenefitWearableTrinket trinket)
  {
    super(new AbleToActPrerequisite(wearer));
    this.wearer = wearer;
    this.trinket = trinket;
  }

  @Override
  public void execute()
  {
    if (wearer.isDead())
    {
      return;
    }
    if (!wearer.getCarrying().isWorn(trinket))
    {
      return;
    }

    trinket.applyBenefit(wearer);
    CommandDelegate.execute(new RunLaterCmd(trinket.getBenefitRepeatDurationMS(), new ApplyTimeBasedBenefitCmd(wearer, trinket)));
  }
}
