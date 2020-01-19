package com.github.dagwud.woodlands.game.messaging;

import java.io.IOException;

public interface IMessageSender {
    void sendMessage(int chatId, String message, String replyMarkup) throws IOException;
    void sendMessage(int chatId, String message) throws IOException;
}
