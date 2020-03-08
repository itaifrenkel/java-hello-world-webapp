package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.battle.EndEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.*;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.*;

import java.util.HashSet;
import java.util.List;
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

  public MoveToLocationCmd(GameCharacter characterToMove, ELocation location, boolean evenIfUnconscious)
  {
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
          SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "<i>Can't go to " + location + " until all party members are in the same place</i>");
          CommandDelegate.execute(cmd);
          return;
        }
        if (anyResting(characterToMove.getParty()))
        {
          SendPartyMessageCmd cmd = new SendPartyMessageCmd(characterToMove.getParty(), "<i>Can't go to " + location + " while some party members are resting</i>");
          CommandDelegate.execute(cmd);
          return;
        }
      }
    }

    endActiveEncounter();

    if (allMoveTogether(location) && !inVillageMove(location, characterToMove.getLocation()))
    {
      doMove(characterToMove.getParty(), location, characterToMove);
    }
    else
    {
      doMove(characterToMove, location, characterToMove);
    }
  }

  private boolean inVillageMove(ELocation moveTo, ELocation currentLocation)
  {
    return currentLocation.isVillageLocation() && moveTo == ELocation.VILLAGE_SQUARE;
  }

  private boolean allMoveTogether(ELocation moveTo)
  {
    return moveTo == ELocation.VILLAGE_SQUARE || !moveTo.isVillageLocation();
  }

  private void doMove(Party partyToMove, ELocation moveTo, GameCharacter movedBy)
  {
    partyToMove.changeLeader(movedBy);
    List<GameCharacter> charactersToMove = partyToMove.getActiveMembers();

    for (GameCharacter character : charactersToMove)
    {
      doMove(character, moveTo, movedBy);
    }

    movedBy.getStats().incrementLeadershipMovesCount();

    if (movedBy instanceof PlayerCharacter)
    {
      PlayerCharacter by = (PlayerCharacter) movedBy;
      EEvent.LED_PARTY.trigger(by);
    }
  }

  private void doMove(GameCharacter characterToMove, ELocation moveTo, GameCharacter movedBy)
  {
    if (characterToMove.getLocation() == moveTo)
    {
      return;
    }

    ELocation from = characterToMove.getLocation();

    produceExitMessage(characterToMove, moveTo, from);

    characterToMove.setLocation(moveTo);

    if (characterToMove instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) characterToMove;

      produceEntryMessage(moveTo, from, character);

      if (characterToMove != movedBy)
      {
        CommandDelegate.execute(new SendMessageCmd(character, "<i>" + movedBy.getName() + " leads you to " + moveTo + "</i>"));
      }
      showMenuForLocation(moveTo, character.getPlayedBy().getPlayerState());
      CommandDelegate.execute(new HandleLocationEntryCmd(moveTo, character.getPlayedBy().getPlayerState()));
    }
  }

  private void produceEntryMessage(ELocation moveTo, ELocation from, PlayerCharacter character)
  {
    String entryText = moveTo.getMenu().produceEntryText(character, from);
    if (entryText != null)
    {
      CommandDelegate.execute(new SendLocationMessageCmd(moveTo, "<i>" + entryText + "</i>", character));
    }
  }

  private void produceExitMessage(GameCharacter characterToMove, ELocation moveTo, ELocation from)
  {
    if (characterToMove instanceof PlayerCharacter)
    {
      PlayerCharacter toMove = (PlayerCharacter) characterToMove;

      if (from != null)
      {
        String exitText = from.getMenu().produceExitText(toMove, moveTo);
        if (exitText != null)
        {
          CommandDelegate.execute(new SendLocationMessageCmd(from, "<i>" + exitText + "</i>", characterToMove));
        }
      }
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
      if (member.isResting())
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

}
