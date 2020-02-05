package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.battle.EndEncounterCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.EnterTheVillageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.log.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MoveToLocationCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter characterToMove;
  private final ELocation location;

  public MoveToLocationCmd(GameCharacter characterToMove, ELocation location)
  {
    super(new AbleToActPrerequisite(characterToMove));
    this.characterToMove = characterToMove;
    this.location = location;
  }

  @Override
  public void execute()
  {
    if (allMoveTogether(location))
    {
      // except for The Village - individual players can go in and out of the Inn/Tavern/etc. independently:
      if (location != ELocation.VILLAGE_SQUARE)
      {
        // location requires whole party to move as one:
        if (!allAtSameLocation(characterToMove.getParty()))
        {
          SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "Can't go to " + location + " until all party members are in the same place");
          CommandDelegate.execute(cmd);
          return;
        }
        if (anyResting(characterToMove.getParty()))
        {
          SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "Can't go to " + location + " while some party members are resting");
          CommandDelegate.execute(cmd);
          return;
        }
      }
    }

    endActiveEncounter();

    if (allMoveTogether(location))
    {
      doMove(characterToMove.getParty().getActiveMembers(), location);
    }
    else
    {
      doMove(characterToMove, location);
    }
  }

  private boolean allMoveTogether(ELocation moveTo)
  {
    return moveTo == ELocation.MOUNTAIN || moveTo == ELocation.VILLAGE_SQUARE || moveTo == ELocation.WOODLANDS;
  }

  private void doMove(Collection<GameCharacter> charactersToMove, ELocation moveTo)
  {
    for (GameCharacter character : charactersToMove)
    {
      doMove(character, moveTo);
    }
  }

  private void doMove(GameCharacter characterToMove, ELocation moveTo)
  {
    Logger.info("Moving " + characterToMove.getName() + " to " + moveTo);
    characterToMove.setLocation(moveTo);

    if (characterToMove instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) characterToMove;
      showMenuForLocation(moveTo, character.getPlayedBy().getPlayerState());
      handleLocationEntry(moveTo, character.getPlayedBy().getPlayerState());
    }
  }

  private void endActiveEncounter()
  {
    if (characterToMove.getParty().getActiveEncounter() != null)
    {
      EndEncounterCmd cmd = new EndEncounterCmd(characterToMove.getParty().getActiveEncounter());
      CommandDelegate.execute(cmd);
    }
  }

  private boolean allAtSameLocation(Party party)
  {
    Set<ELocation> locations = new HashSet<>();
    for (GameCharacter member : party.getActiveMembers())
    {
      locations.add(member.getLocation());
    }
    return locations.size() == 1;
  }

  private boolean anyResting(Party party)
  {
    for (GameCharacter member : party.getActiveMembers())
    {
      if (member.getStats().getState() == EState.RESTING)
      {
        return true;
      }
    }
    return false;
  }

  private void showMenuForLocation(ELocation location, PlayerState playerState)
  {
    ShowMenuCmd cmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(cmd);
  }

  private void handleLocationEntry(ELocation location, PlayerState playerState)
  {
    if (location == ELocation.MOUNTAIN)
    {
      EnterTheMountainCmd cmd = new EnterTheMountainCmd(playerState);
      CommandDelegate.execute(cmd);
    }

    if (location == ELocation.VILLAGE_SQUARE)
    {
      EnterTheVillageCmd cmd = new EnterTheVillageCmd(playerState.getActiveCharacter());
      CommandDelegate.execute(cmd);
    }
  }
}
