package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.sun.javafx.sg.prism.NGAmbientLight;

public class CharacterIsSetUpPrecondition implements CommandPrerequisite
{
  private final int chatId;
  private final GameCharacter character;

  public CharacterIsSetUpPrecondition(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public boolean verify()
  {
    if (character != null && character.isSetupComplete())
    {
      return true;
    }
    CommandDelegate.execute(new SendMessageCmd(chatId, "You don't have a character at the moment. Try `/new` to create a character"));
    return false;
  }
}
