package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

import java.math.BigDecimal;

public class BuyDrinksCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter activeCharacter;

  public BuyDrinksCmd(int chatId, GameCharacter activeCharacter)
  {
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    if (activeCharacter.getStats().getHitPoints() <= 1)
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

      modifyDrunkeness();
    }
    else
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "You don't know what the drink the barman puts down in front of you is, but it doesn't look good. You drink it anyway... you don't feel good.");
      CommandDelegate.execute(cmd);

      modifyDrunkeness();
      modifyDrunkeness();
    }
  }

  private void modifyDrunkeness()
  {
    int drunkeness = activeCharacter.getStats().getDrunkeness() + 1;
    if (drunkeness % 5 == 0)
    {
      ReduceHitPointsCmd damage = new ReduceHitPointsCmd(activeCharacter, 1);
      CommandDelegate.execute(damage);
    }

    activeCharacter.getStats().setDrunkeness(drunkeness);
  }
}
