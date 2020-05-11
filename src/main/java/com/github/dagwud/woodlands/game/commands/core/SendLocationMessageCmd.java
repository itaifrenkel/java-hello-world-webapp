package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class SendLocationMessageCmd extends AbstractCmd
{
  private ELocation eLocation;
  private String message;

  private GameCharacter originator;

  private boolean partyOnly = false;

  public SendLocationMessageCmd(ELocation eLocation, String message)
  {
    this.eLocation = eLocation;
    this.message = message;
  }

  public SendLocationMessageCmd(ELocation eLocation, String message, GameCharacter originator, boolean partyOnly)
  {
    this.eLocation = eLocation;
    this.message = message;
    this.originator = originator;
    this.partyOnly = partyOnly;
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
    List<Fighter> charactersInRoom = new ArrayList<>(eLocation.getCharactersInRoom());

    for (Fighter gameCharacter : charactersInRoom)
    {
      if (gameCharacter == originator)
      {
        continue;
      }

      if (gameCharacter instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter) gameCharacter;
        if (!partyOnly || character.getParty().getAllMembers().contains(originator))
        {
          CommandDelegate.execute(new SendMessageCmd(character, message));
        }
      }
    }
  }
}
