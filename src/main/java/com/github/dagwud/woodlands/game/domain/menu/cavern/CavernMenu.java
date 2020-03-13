package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.commands.Command;
import com.github.dagwud.woodlands.game.commands.ICommand;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationAndMakeARemarkCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.location.cavern.DoubleEntendres;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class CavernMenu extends GameMenu
{
  CavernMenu()
  {
    setPrompt("You are in a cavern");
  }

  @Override
  public ICommand[] getOptions()
  {
    if (null == super.getOptions())
    {
      List<ICommand> options = new ArrayList<>();
      populateMenuOption(options, buildBackCommand());
      populateMenuOption(options, buildSidePathCommand());
      populateMenuOption(options, buildDeeperCommand());
      setOptions(buildOptions(options.toArray(new ICommand[0])));
    }
    return super.getOptions();
  }

  private void populateMenuOption(List<ICommand> options, ICommand cmd)
  {
    if (cmd != null)
    {
      options.add(cmd);
    }
  }

  private ICommand[] buildOptions(ICommand[] options)
  {
    return options; // todo shuffle them
  }

  private ICommand buildBackCommand()
  {
    ELocation destination = getBackTowardsEntranceLocation();
    if (destination == null)
    {
      return null;
    }
    String remark = getRandomExitRemark();
    return new Command("Back towards entrance", true,
            (character, chatId) -> new MoveToLocationAndMakeARemarkCmd(character, destination, remark));
  }

  private ICommand buildDeeperCommand()
  {
    ELocation destination = getDeeperLocation();
    if (destination == null)
    {
      return null;
    }
    String remark = getRandomEntryRemark();
    return new Command("Deeper", true,
            (character, chatId) -> new MoveToLocationAndMakeARemarkCmd(character, destination, remark));
  }

  protected String getRandomEntryRemark()
  {
    List<String> entryQuips = new DoubleEntendres().getEntryQuips();
    return  entryQuips.get((int) Math.floor(Math.random() * entryQuips.size()));
  }

  protected String getRandomExitRemark()
  {
    List<String> exitQuips = new DoubleEntendres().getExitQuips();
    return  exitQuips.get((int) Math.floor(Math.random() * exitQuips.size()));
  }

  private ICommand buildSidePathCommand()
  {
    ELocation destination = getSidePathLocation();
    if (destination == null)
    {
      return null;
    }
    return new Command("Side Path", true,
            (character, chatId) -> new MoveToLocationCmd(character, destination));
  }

  protected abstract ELocation getBackTowardsEntranceLocation();

  abstract ELocation getDeeperLocation();

  ELocation getSidePathLocation()
  {
    return null;
  }
}
