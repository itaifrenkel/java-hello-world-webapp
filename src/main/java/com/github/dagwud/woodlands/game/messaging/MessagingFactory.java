package com.github.dagwud.woodlands.game.messaging;

import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

public class MessagingFactory
{
    private static MessagingFactory instance;
    private IMessageSender iMessageSender;

    public MessagingFactory(IMessageSender iMessageSender)
    {
        this.iMessageSender = iMessageSender;
    }

    public static MessagingFactory create(IMessageSender iMessageSender)
    {
        if (instance == null)
        {
            buildInstance(iMessageSender);
        }

        return instance;
    }

    public static MessagingFactory create()
    {
        return create(new TelegramMessageSender());
    }

    public IMessageSender sender()
    {
        return iMessageSender;
    }

    private static synchronized void buildInstance(IMessageSender iMessageSender)
    {
        if (instance == null)
        {
            instance = new MessagingFactory(iMessageSender);
        }
    }
}
