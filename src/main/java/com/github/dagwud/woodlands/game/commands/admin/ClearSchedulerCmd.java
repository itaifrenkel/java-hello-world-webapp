package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ClearSchedulerCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;

  public ClearSchedulerCmd(int chatId)
  {
    super(chatId);
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    Scheduler.instance().clear();

    SendMessageCmd cmd = new SendMessageCmd(chatId, "Scheduler cleared");
    CommandDelegate.execute(cmd);
  }
}
