package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class MoveToLocationCmd extends AbstractCmd
{
  private final GameCharacter characterToMove;
  private final ELocation location;

  public MoveToLocationCmd(GameCharacter characterToMove, ELocation location)
  {
    this.characterToMove = characterToMove;
    this.location = location;
  }

  @Override
  public void execute()
  {
    int chatId = characterToMove.getPlayedBy().getChatId();

    if (characterToMove.getParty().getActiveEncounter() != null)
    {
      characterToMove.getParty().getActiveEncounter().end();
      characterToMove.getParty().setActiveEncounter(null);
    }

    if (!characterToMove.isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId,"You need to create a character first. Please use /new");
      CommandDelegate.execute(cmd);
      return;
    }


    if (location == ELocation.MOUNTAIN || location == ELocation.VILLAGE || location == Location.WOODLANDS)
    {
      // location requires whole party to move as one:
      if (!allAtSameLocation(characterToMove.getParty()))
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "Can't go to " + location + " until all party members are in the same place");
        CommandDelegate.execute(cmd);
        return;
      }
      for (GameCharacter character : characterToMove.getParty().getMembers())
      {
        character.setLocation(location);
      }
      SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "You are now at " + location);
      CommandDelegate.execute(cmd);

      for (GameCharacter character : characterToMove.getParty().getMembers())
      {
        showMenuForLocation(location, character.getPlayedBy().getPlayerState());
        handleLocationEntry(location, character.getPlayedBy().getPlayerState());
      }
    }
    else
    {
      // players can move individually:
      characterToMove.setLocation(location);
      SendPartyMessageCmd c = new SendPartyMessageCmd(characterToMove.getParty(), characterToMove.getName() + " is now at " + location);
      CommandDelegate.execute(c);

      showMenuForLocation(location, characterToMove.getPlayedBy().getPlayerState());
      handleLocationEntry(location, characterToMove.getPlayedBy().getPlayerState());
    }
  }

  private boolean areAllAtSameLocation(Party party)
  {
    for (GameCharacter member : party.getMembers())
    {
      locations.add(member.getLocation());
    }
    return locations.size() == 1
  }

  private void showMenuForLocation(ELocation location, PlayerState playerState)
  {
    ShowMenuCmd cmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(cmd);
  }

  private void handleLocationEntry(ELocation location, PlayerState playerState)
  {
    if (playerState.getActiveCharacter().getParty().isLedBy(playerState.getActiveCharacter()))
    {
      if (location == ELocation.MOUNTAIN)
      {
        EnterTheMountainCmd cmd = new EnterTheMountainCmd(playerState);
        CommandDelegate.execute(cmd);
      }
    }
  }
}
