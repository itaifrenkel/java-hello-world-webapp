package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class RollShortRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter character;
  private int recoveredHitPoints;

  public RollShortRestCmd(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    int diceroll = roll(stats.getLevel(), stats.getRestDiceFace());
    int newHitPoints = (stats.getHitPoints() + diceroll + Math.max(stats.getConstitutionModifier(), 0));
    if (newHitPoints > stats.getMaxHitPoints().total())
    {
      newHitPoints = stats.getMaxHitPoints().total();
    }
    recoveredHitPoints = newHitPoints - stats.getHitPoints();
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
