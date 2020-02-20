package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.NonPlayerCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.HerbalRemedy;

import java.util.ArrayList;
import java.util.List;

public class BeginHerbalRemedyCmd extends SuspendableCmd
{
  public BeginHerbalRemedyCmd(PlayerState playerState)
  {
    super(playerState, 2);
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForTarget();
        break;
      case 1:
        if (capturedInput.equals("Cancel"))
        {
          new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "Puff-puff-hold-onto. Not cool bro.").go();
          return;
        }

        GameCharacter gameCharacter = findCharacter(capturedInput);

        new CastSpellCmd(new HerbalRemedy(getPlayerState().getActiveCharacter(), gameCharacter)).go();

        break;
    }
  }

  private GameCharacter findCharacter(String capturedInput)
  {
    PlayerCharacter activeCharacter = getPlayerState().getActiveCharacter();

    for (GameCharacter gameCharacter : activeCharacter.getParty().getActiveMembers())
    {
      if (gameCharacter.getName().equals(capturedInput) && validTarget(activeCharacter, gameCharacter))
      {
        return gameCharacter;
      }
    }
    return null;
  }

  private void promptForTarget()
  {
    String[] homeys = buildPlayerList(getPlayerState());
    ChoiceCmd choice = new ChoiceCmd(getPlayerState().getPlayer().getChatId(), "To who shall you offer the Herb™?", homeys);
    CommandDelegate.execute(choice);
  }

  private String[] buildPlayerList(PlayerState playerState)
  {
    PlayerCharacter activeCharacter = playerState.getActiveCharacter();
    List<String> partyMembersInLocation = new ArrayList<>();
    for (GameCharacter gameCharacter : activeCharacter.getParty().getActiveMembers())
    {
      if (validTarget(activeCharacter, gameCharacter))
      {
        partyMembersInLocation.add(gameCharacter.getName());
      }
    }
    partyMembersInLocation.add("Cancel");

    return partyMembersInLocation.toArray(new String[0]);
  }

  private boolean validTarget(PlayerCharacter activeCharacter, GameCharacter gameCharacter)
  {
    return !(gameCharacter instanceof NonPlayerCharacter) && gameCharacter.getLocation().equals(activeCharacter.getLocation());
  }
}
