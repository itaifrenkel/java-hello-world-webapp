package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class RequireAlivePrerequisite implements CommandPrerequisite
{
  private final GameCharacter character;

  public RequireAlivePrerequisite(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public boolean verify()
  {
    if (character.getStats().getState() != EState.ALIVE)
    {
      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You're " + joiner.getStats().getState().name().toLowerCase() + "; you can't do anything");
      CommandDelegate.execute(cmd);
      return false;
    }
    return true;
  }
}
