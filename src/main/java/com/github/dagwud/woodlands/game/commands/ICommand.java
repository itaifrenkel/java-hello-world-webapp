package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public interface ICommand
{
  boolean isMenuCmd();

  AbstractCmd build(PlayerCharacter activeCharacter, int chatId, String text);

  String getMenuText();
}
