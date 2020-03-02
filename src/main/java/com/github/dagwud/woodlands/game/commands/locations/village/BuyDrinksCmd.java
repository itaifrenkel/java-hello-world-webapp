package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.EState;
import java.math.BigDecimal;

public class BuyDrinksCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private static final int MAX_WARNING_DRINKS_1 = 20;
  private static final int MAX_WARNING_DRINKS_2 = 40;
  private static final long DRINK_DURATION_MS = 30_000;

  private final int chatId;
  private final PlayerCharacter activeCharacter;

  public BuyDrinksCmd(int chatId, PlayerCharacter activeCharacter)
  {
    super(new AbleToActPrerequisite(activeCharacter));
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }

  public BuyDrinksCmd(int chatId, PlayerCharacter activeCharacter, boolean isWake)
  {
    // no precondition - for a wake, you can drink while drinking
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }


  @Override
  public void execute()
  {
    if (activeCharacter.isDead())
    {
      // for wake, we can end up here
      return;
    }

    if (activeCharacter.getStats().getLevel() <= 1)
    {
      SendMessageCmd c = new SendMessageCmd(chatId, "The barman laughs as you politely request a drink. \"Come back when you've earned the right to drink with the grown-ups,\" he scoffs");
      CommandDelegate.execute(c);
      return;
    } 

    if (activeCharacter.getStats().getDrunkeness() > MAX_WARNING_DRINKS_1)
    {
      String warning = "\"Are you sure that's a good idea?\" asks the barman. But he serves you anyway";
      if (activeCharacter.getStats().getDrunkeness() > MAX_WARNING_DRINKS_2)
      {
        warning = "\"It's your funeral, pal\"";
      }
      SendMessageCmd c = new SendMessageCmd(chatId, warning);
      CommandDelegate.execute(c);
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
    activeCharacter.getStats().setState(EState.DRINKING);
    CommandDelegate.execute(new RunLaterCmd(DRINK_DURATION_MS, new FinishDrinkingCmd(activeCharacter)));
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
