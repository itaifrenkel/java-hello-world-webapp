package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.location.tavern.JukeBox;

import java.util.List;

public class ListSongsCmd extends AbstractCmd
{
  public ListSongsCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    JukeBox jukeBox = GameStatesRegistry.instance().getJukeBox();
    for (String lyric : jukeBox.getLyrics())
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, lyric));
    }
  }
}
