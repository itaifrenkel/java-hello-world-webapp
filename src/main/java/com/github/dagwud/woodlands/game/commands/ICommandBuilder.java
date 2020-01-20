package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

@FunctionalInterface
public interface ICommandBuilder
{
    AbstractCmd build(PlayerState playerState, int chatId);
}
