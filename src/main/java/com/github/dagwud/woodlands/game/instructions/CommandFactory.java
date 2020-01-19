package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.*;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.AcceptInputCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.BuyDrinksCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RetrieveItemsCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.ShortRestCmd;
import com.github.dagwud.woodlands.game.commands.start.PlayerSetupCmd;
import com.github.dagwud.woodlands.game.commands.start.StartCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
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

    public AbstractCmd create(Update telegramUpdate, GameState gameState)
    {
        String cmd = telegramUpdate.message.text;

        SuspendableCmd waiting = gameState.getWaitingForInputCmd();
        if (waiting != null)
        {
            return new AcceptInputCmd(waiting, cmd);
        }

        int chatId = telegramUpdate.message.chat.id;
        ECommand by = ECommand.by(cmd);

        if (by != null && (!by.isMenuCmd() || gameState.getCurrentMenu().containsOption(cmd)))
        {
            return by.build(gameState, chatId);
        }

        return new SendMessageCmd(chatId, "I'm not sure what you mean... perhaps try /help?");
    }

}
