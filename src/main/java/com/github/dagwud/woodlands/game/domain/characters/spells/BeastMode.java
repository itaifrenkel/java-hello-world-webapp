package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

import java.math.BigDecimal;

public class BeastMode extends PassiveBattleRoundSpell
{
  private static final long serialVersionUID = 1L;
  private static final BigDecimal PERCENT_CHANCE_PER_LEVEL = new BigDecimal("5");

  private int boost;

  public BeastMode(Fighter caster)
  {
    super("Beast Mode", caster);
  }

  @Override
  public boolean shouldCast()
  {
    BigDecimal level = new BigDecimal(getCaster().getStats().getLevel());
    ChanceCalculatorCmd cmd = new ChanceCalculatorCmd(PERCENT_CHANCE_PER_LEVEL.multiply(level));
    CommandDelegate.execute(cmd);
    return cmd.getResult();
  }

  @Override
  public boolean cast()
  {
    boost = 20; // equivalent of a natural d20 - guaranteed to cause a critical hit
    getCaster().getStats().setCriticalStrikeChanceBonus(getCaster().getStats().getCriticalStrikeChanceBonus() + boost);
    return true;
  }

  @Override
  public void expire()
  {
    getCaster().getStats().setCriticalStrikeChanceBonus(getCaster().getStats().getCriticalStrikeChanceBonus() - boost);
  }

}
