package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class RollShortRestCmd extends AbstractCmd
{
  private final GameCharacter character;
  private int recoveredHitPoints;

  RollShortRestCmd(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int diceroll = roll(character.getStats().getLevel(), character.getCharacterClass().getInitialStats().getShortRestDice());
    int boostFromConstitution = (int) (Math.floor((character.getStats().getConstitution() - 10) / 2.0));
    int newHitPoints = (character.getStats().getHitPoints() + diceroll + boostFromConstitution);
    if (newHitPoints > character.getStats().getMaxHitPoints())
    {
      newHitPoints = character.getStats().getMaxHitPoints();
    }
    recoveredHitPoints = newHitPoints - character.getStats().getHitPoints();
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
