package com.github.dagwud.woodlands.game.commands.locations.cavern;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

public class EnterTheCavernCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerState playerState;
  private final ELocation cavernLocation;

  public EnterTheCavernCmd(PlayerState playerState, ELocation cavernLocation)
  {
    super(new CharacterIsSetUpPrecondition(playerState.getPlayer().getChatId(), playerState.getActiveCharacter()));
    this.playerState = playerState;
    this.cavernLocation = cavernLocation;
  }

  @Override
  public void execute()
  {
    if (!playerState.getActiveCharacter().getParty().isLedBy(playerState.getActiveCharacter()))
    {
      return;
    }

    ECavernEnemyGroup enemies = determineEnemyInfo(cavernLocation);

    RunLaterCmd cmd = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS,
            new GenerateCavernEncounterCmd(playerState, cavernLocation, enemies.enemyCount,
                    enemies.minDifficulty, enemies.maxDifficulty));
    CommandDelegate.execute(cmd);
  }

  private ECavernEnemyGroup determineEnemyInfo(ELocation cavernLocation)
  {
    switch (cavernLocation)
    {
      case CAVERN_1:
        return ECavernEnemyGroup.CAVERN_1;
      case CAVERN_2:
        return ECavernEnemyGroup.CAVERN_2;
      case CAVERN_3:
        return ECavernEnemyGroup.CAVERN_3;
      case CAVERN_4:
        return ECavernEnemyGroup.CAVERN_4;
      case CAVERN_5:
        return ECavernEnemyGroup.CAVERN_5;
      case CAVERN_6:
        return ECavernEnemyGroup.CAVERN_6;
      case CAVERN_7:
        return ECavernEnemyGroup.CAVERN_7;
      case CAVERN_8:
        return ECavernEnemyGroup.CAVERN_8;
    }
    throw new WoodlandsRuntimeException("No definition found for location " + cavernLocation);
  }
}
