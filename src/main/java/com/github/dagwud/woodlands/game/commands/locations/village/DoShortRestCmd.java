package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RollShortRestCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class DoShortRestCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  DoShortRestCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    RollShortRestCmd roll = new RollShortRestCmd(character);
    CommandDelegate.execute(roll);

    int hitPointsRecovered = roll.getRecoveredHitPoints();
    character.getStats().setHitPoints(character.getStats().getHitPoints() + hitPointsRecovered);

    SendMessageCmd echo = new SendMessageCmd(chatId, "You rested and recovered ❤️" + hitPointsRecovered + ". " +
            "Now at " + character.getStats().getHitPoints() + " of " + character.getStats().getMaxHitPoints());
    CommandDelegate.execute(echo);
  }
}
