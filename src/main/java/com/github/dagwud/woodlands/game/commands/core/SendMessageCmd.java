package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

import java.io.IOException;

public class SendMessageCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final String message;
  private final String replyMarkup;

  public SendMessageCmd(int chatId, String message)
  {
    this(chatId, message, null);
  }

  public SendMessageCmd(PlayerCharacter sendTo, String message)
  {
    this(sendTo.getPlayedBy().getChatId(), message, null);
  }

  SendMessageCmd(int chatId, String message, String replyMarkup)
  {
    this.chatId = chatId;
    this.message = message;
    this.replyMarkup = replyMarkup;
  }

  @Override
  public void execute()
  {
    PlayerState currentPlayerStateLookup = GameStatesRegistry.lookup(chatId);

    String newMessage = message;
    if (currentPlayerStateLookup.getPlayer().getPlayerState() != null)
    {
      if (currentPlayerStateLookup.getPlayer().getActiveCharacter() != null)
      {
        Stats stats = currentPlayerStateLookup.getPlayer().getActiveCharacter().getStats();

        if (stats != null)
        {
          DrunkUpMessageCmd cmd = new DrunkUpMessageCmd(newMessage, stats.getDrunkeness());
          CommandDelegate.execute(cmd);
          newMessage = cmd.getMessage();
        }
      }
    }

    // best attempt only because users can block bot:
    try
    {
      MessagingFactory.create().sender().sendMessage(chatId, newMessage, replyMarkup);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
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
