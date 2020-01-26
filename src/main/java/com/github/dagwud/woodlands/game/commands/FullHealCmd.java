package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class FullHealCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  FullHealCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    int heal = character.getStats().getMaxHitPoints() - character.getStats().getHitPoints();
    RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(character, heal);
    CommandDelegate.execute(cmd);
  }
}
