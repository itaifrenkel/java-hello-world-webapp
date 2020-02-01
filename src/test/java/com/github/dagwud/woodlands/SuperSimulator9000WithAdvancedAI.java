package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.messaging.MessagingFactory;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.gson.telegram.User;
import com.github.dagwud.woodlands.web.TelegramServlet;

import java.util.Scanner;

/**
 * A super advanced simulator doing super advanced things.
 */
public class SuperSimulator9000WithAdvancedAI
{

    public static void main(String[] args) throws Exception
    {
        Scanner in = new Scanner(System.in);
        TelegramServlet telegramServlet = new TelegramServlet();

        MessagingFactory.create(new SimulatorSender());

        System.out.println("Send a /start to begin.");
        while (true)
        {
            String s = in.nextLine();
            Update update = createUpdate(s);
            telegramServlet.processTelegramUpdate(update);
        }
    }

    private static Update createUpdate(String messageText)
    {
        Update u = new Update();
        u.message = new Message();
        u.message.text = messageText;
        u.message.chat = new Chat();
        u.message.chat.id = -1;
        u.message.from = new User();
        return u;
    }
}
