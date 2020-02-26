package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RollShortRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter character;
  private int recoveredHitPoints;
  private final BigDecimal percentScale;

  public RollShortRestCmd(GameCharacter character)
  {
    this(character, new BigDecimal("100"));
  }

  public RollShortRestCmd(GameCharacter character, BigDecimal percentageOfNormal)
  {
    this.character = character;
    this.percentScale = percentageOfNormal;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    int diceroll = roll(stats.getLevel(), stats.getRestDiceFace());
    diceroll = rollRecoveryAmount(diceroll, percentScale);

    int newHitPoints = (stats.getHitPoints() + diceroll + Math.max(stats.getConstitutionModifier(), 0));
    if (newHitPoints > stats.getMaxHitPoints().total())
    {
      newHitPoints = stats.getMaxHitPoints().total();
    }
    recoveredHitPoints = newHitPoints - stats.getHitPoints();
  }

  private int rollRecoveryAmount(int diceroll, BigDecimal percentScale)
  {
    BigDecimal scaledDiceRoll = new BigDecimal(diceroll)
            .multiply(percentScale)
            .divide(new BigDecimal("100"), new MathContext(0, RoundingMode.FLOOR));
    diceroll = scaledDiceRoll.intValue();
    if (this.percentScale.compareTo(new BigDecimal("100")) != 0)
    {
      diceroll = Math.min(1, diceroll);
    }
    return diceroll;
  }

  private int roll(int diceCount, int diceFaces)
  {
    DiceRollCmd cmd = new DiceRollCmd(diceCount, diceFaces);
    CommandDelegate.execute(cmd);
    return cmd.getTotal();
  }

  public int getRecoveredHitPoints()
  {
    return recoveredHitPoints;
  }
}
