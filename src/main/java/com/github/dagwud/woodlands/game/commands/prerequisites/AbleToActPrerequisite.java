package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AbleToActPrerequisite implements CommandPrerequisite
{
  private final Fighter fighter;

  public AbleToActPrerequisite(Fighter character)
  {
    this.fighter = character;
  }

  @Override
  public boolean verify()
  {
    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) fighter;
      if (!character.isSetupComplete())
      {
        SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You need to create a character first. Please use /new");
        CommandDelegate.execute(cmd);
        return false;
      }
    }
    if (!fighter.isConscious())
    {
      if (fighter instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter) fighter;
        SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You're " + character.getStats().getState().name().toLowerCase() + "; you can't do anything");
        CommandDelegate.execute(cmd);
      }
      return false;
    }
    return true;
  }
}
