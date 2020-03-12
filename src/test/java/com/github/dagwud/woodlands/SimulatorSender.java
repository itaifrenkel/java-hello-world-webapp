package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.messaging.IMessageSender;

import java.io.IOException;

public class SimulatorSender implements IMessageSender
{
    @Override
    public void sendMessage(long chatId, String message, String replyMarkup)
    {
        String prefix = buildPrefix(chatId);
        message = message.replaceAll("\n", "\n" + prefix);
        System.out.println(prefix + message);
        if (replyMarkup != null)
        {
            System.out.println(prefix + "  Markup: " + replyMarkup);
        }
    }

    @Override
    public void sendMessage(long chatId, String message)
    {
        System.out.println(buildPrefix(chatId) + message);
    }

    private String buildPrefix(long chatId)
    {
        return chatId + "> ";
    }
}
