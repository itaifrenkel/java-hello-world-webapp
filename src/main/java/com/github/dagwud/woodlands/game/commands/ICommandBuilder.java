package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

@FunctionalInterface
public interface ICommandBuilder
{
    AbstractCmd build(PlayerCharacter character, int chatId);
}
