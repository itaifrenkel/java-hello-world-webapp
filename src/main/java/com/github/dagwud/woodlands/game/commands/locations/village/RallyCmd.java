package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.battle.EndEncounterCmd;

import com.github.dagwud.woodlands.game.commands.locations.deepwoods.EnterDeepWoodsCmd;
import com.github.dagwud.woodlands.game.commands.locations.gorge.EnterTheGorgeCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterThePettingZooCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.EnterTheVillageCmd;
import com.github.dagwud.woodlands.game.commands.locations.woodlands.EnterTheWoodlandsCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RallyCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter rallier;

  public RallyCmd(GameCharacter rallier)
  {
    super(new AbleToActPrerequisite(rallier));
    this.rallier = rallier;
  }

  @Override
  public void execute()
  {
    ELocation moveTo = rallier.getLocation();
    SendPartyMessageCmd msg = new SendPartyMessageCmd(rallier.getParty(), "<b>" + rallier.getName() + " sounds the call; all able fighters are duty-bound to rally at " + moveTo.getDisplayName() + "!</b>");
    CommandDelegate.execute(msg);

    for (GameCharacter character : rallier.getParty().getActiveMembers())
    {
      doMove(character, rallier, moveTo);
    }
  }

  private void doMove(GameCharacter characterToMove, GameCharacter movedBy, ELocation moveTo)
  {
    if (characterToMove.getLocation() == moveTo)
    {
      return;
    }

    if (characterToMove != movedBy && characterToMove.isConscious())
    {
      characterToMove.setLocation(moveTo);

      if (characterToMove instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter) characterToMove;
        CommandDelegate.execute(new SendMessageCmd(character, "<i>You have moved to " + moveTo.getDisplayName() + "</i>"));
        showMenuForLocation(moveTo, character.getPlayedBy().getPlayerState());
        handleLocationEntry(moveTo, character.getPlayedBy().getPlayerState());
      }
      if (movedBy instanceof PlayerCharacter)
      {
        PlayerCharacter mover = (PlayerCharacter)movedBy;
        CommandDelegate.execute(new SendMessageCmd(mover, "<i> " + characterToMove.getName() + " has responded to your call</i>"));
      }
    }
    else
    {
      if (characterToMove instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter)characterToMove;
        CommandDelegate.execute(new SendMessageCmd(character, "<i>You are unable to respond to the call. Shame on you.</i>"));
      }
      if (movedBy instanceof PlayerCharacter)
      {
        PlayerCharacter mover = (PlayerCharacter)movedBy;
        CommandDelegate.execute(new SendMessageCmd(mover, "<i> " + characterToMove.getName() + " is unable to respond to the call</i>"));
      }
    }
  }

  private void showMenuForLocation(ELocation location, PlayerState playerState)
  {
    ShowMenuCmd cmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(cmd);
  }

  private void handleLocationEntry(ELocation location, PlayerState playerState)
  {
    switch (location)
    {
      case PETTING_ZOO:
        CommandDelegate.execute(new EnterThePettingZooCmd(playerState));
        break;
       case MOUNTAIN:
        CommandDelegate.execute(new EnterTheMountainCmd(playerState));
        break;
      case WOODLANDS:
        CommandDelegate.execute(new EnterTheWoodlandsCmd(playerState));
        break;
      case DEEP_WOODS:
        CommandDelegate.execute(new EnterDeepWoodsCmd(playerState));
        break;
      case THE_GORGE:
        CommandDelegate.execute(new EnterTheGorgeCmd(playerState));
        break;
      case VILLAGE_SQUARE:
        CommandDelegate.execute(new EnterTheVillageCmd(playerState.getActiveCharacter()));
        break;
    }
  }
}
