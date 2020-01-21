package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class AbleToActPrerequisite implements CommandPrerequisite
{
  private final GameCharacter character;

  public AbleToActPrerequisite(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public boolean verify()
  {
    if (!character.isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(),"You need to create a character first. Please use /new");
      CommandDelegate.execute(cmd);
      return false;
    }
    if (character.getStats().getState() != EState.ALIVE)
    {
      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You're " + character.getStats().getState().name().toLowerCase() + "; you can't do anything");
      CommandDelegate.execute(cmd);
      return false;
    }
    return true;
  }
}
