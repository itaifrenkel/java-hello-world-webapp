package com.github.dagwud.woodlands.game.messaging;

import java.io.IOException;

public interface IMessageSender {
    void sendMessage(long chatId, String message, String replyMarkup) throws IOException;
    void sendMessage(long chatId, String message) throws IOException;
}
