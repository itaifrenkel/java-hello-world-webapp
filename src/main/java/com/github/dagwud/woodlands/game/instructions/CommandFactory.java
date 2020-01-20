package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.*;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.AcceptInputCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.gson.telegram.Update;

public class CommandFactory
{
    private static CommandFactory instance;

    public static CommandFactory instance()
    {
        if (null == instance)
        {
            createInstance();
        }
        return instance;
    }

    private synchronized static void createInstance()
    {
        if (instance != null)
        {
            return;
        }
        instance = new CommandFactory();
    }

    public AbstractCmd create(Update telegramUpdate, PlayerState playerState)
    {
        String cmd = telegramUpdate.message.text;

        SuspendableCmd waiting = playerState.getWaitingForInputCmd();
        if (waiting != null)
        {
            return new AcceptInputCmd(waiting, cmd);
        }

        int chatId = telegramUpdate.message.chat.id;
        ECommand by = ECommand.by(cmd);

        if (by != null && (!by.isMenuCmd() || playerState.getCurrentMenu().containsOption(cmd)))
        {
            return by.build(playerState.getActiveCharacter(), chatId);
        }

        return new SendMessageCmd(chatId, "I'm not sure what you mean... perhaps try /help?");
    }

}
