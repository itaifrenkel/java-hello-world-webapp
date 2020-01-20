package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.messaging.IMessageSender;

import java.io.IOException;

public class SimulatorSender implements IMessageSender
{
    @Override
    public void sendMessage(int chatId, String message, String replyMarkup)
    {
        System.out.println(chatId + ">   " + message);
        if (replyMarkup != null)
        {
            System.out.println(chatId + ">   Markup: " + replyMarkup);
        }
    }

    @Override
    public void sendMessage(int chatId, String message) throws IOException
    {
        System.out.println(message);
    }
}
