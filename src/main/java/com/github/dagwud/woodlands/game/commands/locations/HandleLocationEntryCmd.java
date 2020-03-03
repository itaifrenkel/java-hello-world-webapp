package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.alchemist.EnterAlchemistCmd;
import com.github.dagwud.woodlands.game.commands.locations.blacksmith.EnterBlacksmithCmd;
import com.github.dagwud.woodlands.game.commands.locations.cavern.EnterTheCavernCmd;
import com.github.dagwud.woodlands.game.commands.locations.deepwoods.EnterDeepWoodsCmd;
import com.github.dagwud.woodlands.game.commands.locations.gorge.EnterTheGorgeCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.commands.locations.pettingzoo.EnterThePettingZooCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.EnterTheVillageCmd;
import com.github.dagwud.woodlands.game.commands.locations.woodlands.EnterTheWoodlandsCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class HandleLocationEntryCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final ELocation location;
  private final PlayerState playerState;

  public HandleLocationEntryCmd(ELocation location, PlayerState playerState)
  {
    this.location = location;
    this.playerState = playerState;
  }


  @Override
  public void execute()
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
      case BLACKSMITH:
        CommandDelegate.execute(new EnterBlacksmithCmd(playerState.getActiveCharacter()));
        break;
      case ALCHEMIST:
        CommandDelegate.execute(new EnterAlchemistCmd(playerState.getActiveCharacter()));
        break;

      case CAVERN_1:
      case CAVERN_2:
      case CAVERN_3:
      case CAVERN_4:
      case CAVERN_5:
      case CAVERN_6:
      case CAVERN_7:
      case CAVERN_8:
        CommandDelegate.execute(new EnterTheCavernCmd(playerState, location));
        break;
    }
  }
}
