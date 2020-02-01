package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class GrantExperienceCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private final int experience;

  public GrantExperienceCmd(PlayerCharacter character, int experience)
  {
    this.character = character;
    this.experience = experience;
  }

  @Override
  public void execute()
  {
    character.getStats().setExperience(character.getStats().getExperience() + experience);
    CheckLevelUpCmd cmd = new CheckLevelUpCmd(character);
    CommandDelegate.execute(cmd);
  }
}
