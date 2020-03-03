package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractRoomCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.location.tavern.JukeBox;

import java.util.List;

public class TavernIntervalCmd extends AbstractRoomCmd
{
  public TavernIntervalCmd()
  {
    super(30_000);
  }

  @Override
  public void execute()
  {
    JukeBox jukeBox = GameStatesRegistry.instance().getJukeBox();
    List<String> lyrics = jukeBox.getLyrics();
    List<String> emissions = jukeBox.getEmissions();

    String lyric = lyrics.get((int) Math.floor(Math.random() * lyrics.size()));
    String emit = emissions.get((int) Math.floor(Math.random() * emissions.size()));

    String result = "<i>" + String.format(emit, lyric) + "</i>";

    CommandDelegate.execute(new SendLocationMessageCmd(ELocation.TAVERN, result));
  }
}
