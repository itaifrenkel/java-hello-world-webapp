package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;

public class InvalidateCreatureCacheCmd extends AdminCmd
{
  private final int chatId;

  public InvalidateCreatureCacheCmd(int chatId)
  {
    super(chatId);
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    CreaturesCacheFactory.invalidate();
    CreaturesCacheFactory.instance();
    SendMessageCmd cmd = new SendMessageCmd(chatId, "Cache cleared");
    CommandDelegate.execute(cmd);
  }
}
