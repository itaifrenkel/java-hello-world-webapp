package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Player;

public class PlayerSetupCmd extends AbstractCmd
{
  private final Player player;
  private final int chatId;

  public PlayerSetupCmd(Player player, int chatId)
  {
    super(new NoActiveCharacterPrerequisite(player));
    this.chatId = chatId;
    this.player = player;
  }

  @Override
  public void execute()
  {
    Player player = GameStatesRegistry.lookup(chatId).getPlayer();
    PlayerState playerState = player.getPlayerState();
    playerState.setPlayer(player);

    DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(player);
    CommandDelegate.execute(cmd);
  }

  public Player getPlayer()
  {
    return player;
  }
}
