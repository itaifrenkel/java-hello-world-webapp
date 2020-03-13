package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.IsAdminOrSomeGuyPrerequisite;
import com.github.dagwud.woodlands.game.domain.location.tavern.JukeBox;

public class AddSongCmd extends SuspendableCmd
{
  public AddSongCmd(PlayerState playerState)
  {
    super(playerState, 2, new IsAdminOrSomeGuyPrerequisite(playerState.getPlayer().getChatId()));
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "Give us a song, you're the admin-man"));
        break;

      case 1:
        String[] split = capturedInput.split("\n");
        for (String song : split)
        {
          persistSong(song);
        }

        break;
    }
  }

  private void persistSong(String capturedInput)
  {
    JukeBox jukeBox = GameStatesRegistry.instance().getJukeBox();
    for (String lyric : jukeBox.getLyrics())
    {
      // just in case
      if (lyric.equalsIgnoreCase(capturedInput))
      {
        CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "Meh, heard it already."));
        return;
      }
    }

    jukeBox.getLyrics().add(capturedInput);

    CommandDelegate.execute(new SendMessageCmd(getPlayerState().getActiveCharacter(), "Righto, added: " + capturedInput));
  }
}
