package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.sun.javafx.sg.prism.NGAmbientLight;

public class CharacterIsSetUpPrecondition implements CommandPrerequisite
{
  private final GameCharacter character;

  public CharacterIsSetUpPrecondition(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public boolean verify()
  {
    if (null == character)
    {
      return false;
    }
    return character.isSetupComplete();
  }
}
