package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

@FunctionalInterface
public interface ICommandBuilder
{
    AbstractCmd build(GameCharacter character, int chatId);
}
