package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

@FunctionalInterface
public interface ICommandBuilder
{
    AbstractCmd build(GameState gameState, int chatId);
}
