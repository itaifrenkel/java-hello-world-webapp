package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class RollShortRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private int recoveredHitPoints;

  RollShortRestCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    int diceroll = roll(stats.getLevel(), stats.getRestDiceFace());
    int boostFromConstitution = (int) (Math.floor((stats.getConstitution().total() - 10) / 2.0));
    int newHitPoints = (stats.getHitPoints() + diceroll + boostFromConstitution);
    if (newHitPoints > stats.getMaxHitPoints())
    {
      newHitPoints = stats.getMaxHitPoints();
    }
    recoveredHitPoints = newHitPoints - stats.getHitPoints();

    stats.setRestPoints(stats.getRestPoints() - 1);
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
