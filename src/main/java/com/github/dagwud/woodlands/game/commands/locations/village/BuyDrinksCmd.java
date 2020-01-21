package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

import java.math.BigDecimal;

public class BuyDrinksCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter activeCharacters;

  public BuyDrinksCmd(int chatId, GameCharacter activeCharacter)
  {
    this.chatId = chatId;
    this.activeCharacters = activeCharacter;
  }

  @Override
  public void execute()
  {
    if (activeCharacters.getStats().getHitPoints() <= 1)
    {
      SendMessageCmd c = new SendMessageCmd(chatId, "\"You've had enough, mate\"");
      CommandDelegate.execute(c);
      return;
    }

    ChanceCalculatorCmd chance = new ChanceCalculatorCmd(new BigDecimal("33.333"));
    chance.execute();
    if (chance.getResult())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "Aaah, a nice cold beer. Just what the druid ordered.");
      CommandDelegate.execute(cmd);
    }
    else
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "You don't know what the drink is the barman puts down in front of you, but it doesn't look good. You drink it anyway.. you don't feel good.");
      CommandDelegate.execute(cmd);

      ReduceHitPointsCmd damage = new ReduceHitPointsCmd(activeCharacters, 1);
      CommandDelegate.execute(damage);
    }
  }
}
