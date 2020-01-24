package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
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
    Player player = new Player(chatId, GameStatesRegistry.lookup(chatId));
    PlayerState playerState = player.getPlayerState();
    playerState.setPlayer(player);
    playerState.getPlayer().setActiveCharacter(new GameCharacter(player));
    playerState.getPlayer().getActiveCharacter().setSetupComplete(false);

    DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(player);
    CommandDelegate.execute(cmd);
  }

  public Player getPlayer()
  {
    return player;
  }
}
