package com.github.dagwud.woodlands.multi;

import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.gson.telegram.User;
import com.github.dagwud.woodlands.web.TelegramServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServeUserThread implements Runnable {
    private final Socket clientSocket;
    private final int chatId;
    private final TelegramServlet telegramServlet;

    private PrintWriter out;

    public ServeUserThread(Socket clientSocket, int chatId, TelegramServlet telegramServlet) {
        this.clientSocket = clientSocket;
        this.chatId = chatId;
        this.telegramServlet = telegramServlet;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String s = in.readLine();

            while (s != null) {
                Update update = createUpdate(s, chatId);
                telegramServlet.processTelegramUpdate(update);
                s = in.readLine();
            }
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }

    private static Update createUpdate(String messageText, int id) {
        Update u = new Update();
        u.message = new Message();
        u.message.text = messageText;
        u.message.chat = new Chat();
        u.message.chat.id = id;
        u.message.from = new User();
        return u;
    }

    public void sendMessage(String message, String replyMarkup) {
        if (replyMarkup != null) {
            out.println(message + " Markup: " + replyMarkup);
        } else {
            out.println(message);
        }
    }
}
