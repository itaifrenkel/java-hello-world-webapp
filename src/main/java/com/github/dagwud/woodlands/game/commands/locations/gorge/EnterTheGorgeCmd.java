package com.github.dagwud.woodlands.game.commands.locations.gorge;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.locations.deepwoods.GenerateDeepWoodsEncounterCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class EnterTheGorgeCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerState playerState;

  public EnterTheGorgeCmd(PlayerState playerState)
  {
    super(new CharacterIsSetUpPrecondition(playerState.getPlayer().getChatId(), playerState.getActiveCharacter()));
    this.playerState = playerState;
  }

  @Override
  public void execute()
  {
    if (!playerState.getActiveCharacter().getParty().isLedBy(playerState.getActiveCharacter()))
    {
      return;
    }

    RunLaterCmd cmd = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS,
            new GenerateGorgeEncounterCmd(playerState));
    CommandDelegate.execute(cmd);

    EEvent.MOVED.trigger(playerState.getActiveCharacter());
  }
}
