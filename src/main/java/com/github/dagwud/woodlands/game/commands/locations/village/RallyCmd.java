package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

import com.github.dagwud.woodlands.game.commands.locations.HandleLocationEntryCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

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

    for (Fighter character : rallier.getParty().getActiveMembers())
    {
      doMove(character, rallier, moveTo);
    }
  }

  private void doMove(Fighter characterToMove, GameCharacter movedBy, ELocation moveTo)
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
        CommandDelegate.execute(new HandleLocationEntryCmd(moveTo, character.getPlayedBy().getPlayerState()));
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

}
