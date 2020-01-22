package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class GrantExperienceCmd extends AbstractCmd
{
  private final GameCharacter character;
  private final int experience;

  public GrantExperienceCmd(GameCharacter character, int experience)
  {
    this.character = character;
    this.experience = experience;
  }

  @Override
  public void execute()
  {
    character.getStats().setExperience(character.getStats().getExperience() + experience);
  }
}
