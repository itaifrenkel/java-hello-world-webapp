package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.ELocation;
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
    SimulatorFrame frame = new SimulatorFrame();
    frame.setVisible(true);

    Scanner in = new Scanner(System.in);
    TelegramServlet telegramServlet = new TelegramServlet();

    Settings.DEVELOPER_MODE = true;

    MessagingFactory.create(new SimulatorSender());

    telegramServlet.processTelegramUpdate(createUpdate("/start", -1));

        // various things don't happen without a persisted state to get at without erroring
        // may behave weird if your persistence works, then comment it out
        ELocation.scheduleRooms();
        EEvent.subscribeToStandardEvents();

    int chatId = -1;
    while (true)
    {
      try
      {
        String s = in.nextLine();
        // allow simulating multiple players by switching chat IDs:
        if (s.startsWith("chatid:-"))
        {
          chatId = Integer.parseInt(s.substring("chatid:".length()));
          System.out.println("*** Simulator now running with chat_id=" + chatId + "***");
        }
        else
        {
          Update update = createUpdate(s, chatId);
          telegramServlet.processTelegramUpdate(update);
        }
      }
      catch (Exception e)
      {
        // smother and keep running
        e.printStackTrace();
      }
    }
  }

  private static Update createUpdate(String messageText, int id)
  {
    Update u = new Update();
    u.message = new Message();
    u.message.text = messageText;
    u.message.chat = new Chat();
    u.message.chat.id = id;
    u.message.from = new User();
    return u;
  }
}
