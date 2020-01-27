package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CheckLevelUpCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  CheckLevelUpCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int correctLevel = determineCorrectLevel(character.getStats().getExperience());
    while (correctLevel < character.getStats().getLevel())
    {
      LevelUpCmd levelUp = new LevelUpCmd(character.getPlayedBy().getChatId(), character);
      CommandDelegate.execute(levelUp);
    }
  }

  private int determineCorrectLevel(int experience)
  {
    if (experience >= 355000) return 20;
    if (experience >= 305000) return 19;
    if (experience >= 265000) return 18;
    if (experience >= 225000) return 17;
    if (experience >= 195000) return 16;
    if (experience >= 165000) return 15;
    if (experience >= 140000) return 14;
    if (experience >= 120000) return 13;
    if (experience >= 100000) return 12;
    if (experience >= 85000) return 11;
    if (experience >= 64000) return 10;
    if (experience >= 48000) return 9;
    if (experience >= 34000) return 8;
    if (experience >= 23000) return 7;
    if (experience >= 14000) return 6;
    if (experience >= 6500) return 5;
    if (experience >= 2700) return 4;
    if (experience >= 900) return 3;
    if (experience >= 300) return 2;
    return 1;
  }
}
