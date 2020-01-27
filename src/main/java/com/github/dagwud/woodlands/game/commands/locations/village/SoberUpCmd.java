package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class SoberUpCmd extends AbstractCmd
{
  private final PlayerCharacter character;
  private final int chatId;

  public SoberUpCmd(PlayerCharacter character, int chatId)
  {
    this.character = character;
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    if (!character.isActive() || character.getStats().getState() == EState.DEAD)
    {
      return;
    }
    Stats stats = character.getStats();
    if (stats.getDrunkeness() != 0)
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "You sober up a bit."));
      stats.setDrunkeness(Math.max(0, stats.getDrunkeness() - 1));
    }
  }
}
