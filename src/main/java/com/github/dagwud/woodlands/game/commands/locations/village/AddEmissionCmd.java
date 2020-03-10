package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.IsAdminOrSomeGuyPrerequisite;
import com.github.dagwud.woodlands.game.domain.location.tavern.JukeBox;

public class AddEmissionCmd extends SuspendableCmd
{
  public AddEmissionCmd(PlayerState playerState)
  {
    super(playerState, 2, new IsAdminOrSomeGuyPrerequisite(playerState.getPlayer().getChatId()));
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "How do you play that funky tune?"));
        break;

      case 1:
        persistEmission(capturedInput);
        break;
    }
  }

  // not for even one moment do I regret naming it an emission
  private void persistEmission(String capturedInput)
  {
    JukeBox jukeBox = GameStatesRegistry.instance().getJukeBox();
    for (String emission : jukeBox.getEmissions())
    {
      // just in case
      if (emission.equalsIgnoreCase(capturedInput))
      {
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "Meh, heard it sung like that before."));
        return;
      }
    }

    jukeBox.getEmissions().add(capturedInput);
    CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "Righto, added emission: " + capturedInput));
  }
}
