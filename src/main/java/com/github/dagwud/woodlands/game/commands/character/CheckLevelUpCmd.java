package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Levels;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CheckLevelUpCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public CheckLevelUpCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int correctLevel = Levels.determineLevel(character.getStats().getExperience());

    while (correctLevel > character.getStats().getLevel())
    {
      LevelUpCmd levelUp = new LevelUpCmd(character.getPlayedBy().getChatId(), character);
      CommandDelegate.execute(levelUp);
    }
  }
}
