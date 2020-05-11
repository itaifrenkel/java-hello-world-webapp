package com.github.dagwud.woodlands.multi;

import com.github.dagwud.woodlands.game.messaging.IMessageSender;

import java.io.IOException;
import java.util.Map;

public class SocketSender implements IMessageSender {
    private final Map<Integer, ServeUserThread> threads;

    public SocketSender(Map<Integer, ServeUserThread> threads) {
        this.threads = threads;
    }

    @Override
    public void sendMessage(long chatId, String message, String replyMarkup) throws IOException {
        if (!threads.containsKey((int)chatId)) {
            throw new IOException("Don't know that chat ID");
        }
        threads.get((int)chatId).sendMessage(message, replyMarkup);
    }

    @Override
    public void sendMessage(long chatId, String message) throws IOException {
        sendMessage(chatId, message, null);
    }
}
