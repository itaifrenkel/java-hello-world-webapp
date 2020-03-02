package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class Command implements ICommand
{
  private final String menuText;
  private final boolean isMenuCmd;
  private final ICommandBuilder commandBuilder;

  public Command(String menuText, boolean isMenuCmd, ICommandBuilder builder)
  {
    this.menuText = menuText;
    this.isMenuCmd = isMenuCmd;
    this.commandBuilder = builder;
  }

  @Override
  public final boolean isMenuCmd()
  {
    return isMenuCmd;
  }

  @Override
  public final AbstractCmd build(PlayerCharacter character, int chatId)
  {
    return commandBuilder.build(character, chatId);
  }

  @Override
  public String getMenuText()
  {
    return menuText;
  }
}
