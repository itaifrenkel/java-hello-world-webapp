package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class SendLocationMessageCmd extends AbstractCmd
{
  private ELocation eLocation;
  private String message;

  private GameCharacter originator;

  public SendLocationMessageCmd(ELocation eLocation, String message)
  {
    this.eLocation = eLocation;
    this.message = message;
  }

  public SendLocationMessageCmd(ELocation eLocation, String message, GameCharacter originator)
  {
    this.eLocation = eLocation;
    this.message = message;
    this.originator = originator;
  }

  @Override
  public void execute()
  {
    // grab a copy to avoid concurrent modification errors.

    List<GameCharacter> charactersInRoom = new ArrayList<>(eLocation.getCharactersInRoom());
    for (GameCharacter gameCharacter : charactersInRoom)
    {
      if (gameCharacter == originator)
      {
        continue;
      }

      // should this be party only?
      if (gameCharacter instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter) gameCharacter;
        CommandDelegate.execute(new SendMessageCmd(character, message));
      }
    }
  }
}
