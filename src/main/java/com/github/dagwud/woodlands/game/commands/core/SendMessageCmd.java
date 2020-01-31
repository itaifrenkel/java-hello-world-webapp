package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

import java.io.IOException;

public class SendMessageCmd extends AbstractCmd
{
    private final int chatId;
    private final String message;
    private final String replyMarkup;

    public SendMessageCmd(int chatId, String message)
    {
        this(chatId, message, null);
    }

    SendMessageCmd(int chatId, String message, String replyMarkup)
    {
        this.chatId = chatId;
        this.message = message;
        this.replyMarkup = replyMarkup;
    }

    @Override
    public void execute() throws IOException
    {
        PlayerState currentPlayerStateLookup = GameStatesRegistry.lookup(chatId);

        String newMessage = message;
        if (currentPlayerStateLookup.getPlayer().getPlayerState() != null)
        {
            if (currentPlayerStateLookup.getPlayer().getActiveCharacter() != null)
            {
                Stats stats = currentPlayerStateLookup.getPlayer().getActiveCharacter().getStats();
                newMessage = drunkFucate(message, stats);
            }
        }
        MessagingFactory.create().sender().sendMessage(chatId, newMessage, replyMarkup);
    }

    private String drunkFucate(String message, Stats stats)
    {
        if (stats == null)
        {
            return message;
        }

        int drunkeness = stats.getDrunkeness();

        if (drunkeness > 2)
        {
            message = message.replaceAll("s", "ss");
        }
        if (drunkeness > 3)
        {
            message = message.replaceAll("c", "s");
        }
        if (drunkeness > 4)
        {
            message = message.replaceAll("s", "SH");
        }
        if (drunkeness > 6)
        {
            message = message.replaceAll("s", "sh").toUpperCase();
        }
        if (drunkeness > 8)
        {
            message = message.replaceAll("\\.", "!").toUpperCase();
        }

        return message;
    }

    @Override
    public String toString()
    {
        return "SendMessageCmd{" +
                "chatId=" + chatId +
                ", message='" + message + '\'' +
                '}';
    }
}
