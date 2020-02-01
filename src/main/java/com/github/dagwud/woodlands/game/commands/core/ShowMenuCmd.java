package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class ShowMenuCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameMenu menu;
  private final PlayerState playerState;

  public ShowMenuCmd(GameMenu menu, PlayerState playerState)
  {
    this.menu = menu;
    this.playerState = playerState;
  }

  @Override
  public void execute()
  {
    ChoiceCmd cmd = new ChoiceCmd(playerState.getPlayer().getChatId(), menu.getPrompt(), menu.getOptions());
    CommandDelegate.execute(cmd);
    playerState.setCurrentMenu(menu);
  }
}
