package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TavernMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  private static final List<String> ENTRY_TEXT =
          Arrays.asList("%s strides into the tavern and strikes a pose.",
                  "%s walks in, looking super thirsty.");

  private static final List<String> EXIT_TEXT =
          Arrays.asList("%s pauses at the door, looks back with regret, and then steps out into the bustling village square.",
                  "All of a sudden, %s stands, grabs their stomach, and runs out the door.");

  public TavernMenu()
  {
    setPrompt("<i>This is the Tavern</i>");
    setOptions(ECommand.BUY_DRINKS, ECommand.WAKE);
  }

  @Override
  public String produceEntryText(PlayerCharacter playerState, ELocation from)
  {
    return produceRandomText(playerState, ENTRY_TEXT);
  }

  private String produceRandomText(PlayerCharacter playerState, List<String> textOptions)
  {
    DiceRollCmd diceRollCmd = new DiceRollCmd(1, textOptions.size());
    diceRollCmd.go();

    return String.format(textOptions.get(diceRollCmd.getTotal() - 1), playerState.getName());
  }

  @Override
  public String produceExitText(PlayerCharacter playerState, ELocation to)
  {
    return produceRandomText(playerState, EXIT_TEXT);
  }

  @Override
  public String[] produceOptions(PlayerState playerState)
  {
    // Arrays.asList produces an immutable list
    List<String> options = new ArrayList<>(Arrays.asList(super.produceOptions(playerState)));
    if (playerState.getActiveCharacter().getStats().getAvailableStatsPointUpgrades() > 0)
    {
      options.add(ECommand.UPGRADE.toString());
    }

    options.add(ECommand.VILLAGE_SQUARE.toString());

    return options.toArray(new String[0]);
  }
}
