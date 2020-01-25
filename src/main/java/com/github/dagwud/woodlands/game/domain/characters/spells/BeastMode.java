package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.domain.characters.Brawler;

import java.math.BigDecimal;

public class BeastMode extends Spell
{
  private Brawler brawler;
  private int boost;

  public BeastMode(Brawler castOn)
  {
    super("Beast Mode", castOn);
    this.brawler = castOn;
  }

  @Override
  public boolean shouldCast()
  {
    BigDecimal PERCENT_CHANCE_PER_LEVEL = new BigDecimal("5");
    BigDecimal level = new BigDecimal(brawler.getStats().getLevel());
    ChanceCalculatorCmd cmd = new ChanceCalculatorCmd(PERCENT_CHANCE_PER_LEVEL.multiply(level));
    CommandDelegate.execute(cmd);
    return cmd.getResult();
  }

  @Override
  public void cast()
  {
    boost = 20; // equivalent of a natural d20 - guaranteed to cause a critical hit
    brawler.getStats().setCriticalStrikeChanceBonus(brawler.getStats().getCriticalStrikeChanceBonus() + boost);
  }

  @Override
  public void expire()
  {
    brawler.getStats().setCriticalStrikeChanceBonus(brawler.getStats().getCriticalStrikeChanceBonus() - boost);
  }
}
