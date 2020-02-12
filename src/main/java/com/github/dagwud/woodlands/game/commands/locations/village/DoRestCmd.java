package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;

import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.RecoverManaCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;


public class DoRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;
  private final boolean isLongRest;

  DoRestCmd(int chatId, PlayerCharacter character, boolean isLongRest)
  {
    this.chatId = chatId;
    this.character = character;
    this.isLongRest = isLongRest;
  }

  @Override
  public void execute()
  {
    if (character.getStats().getState() == EState.DEAD)
    {
      return;
    }

    RollShortRestCmd roll = new RollShortRestCmd(character);
    CommandDelegate.execute(roll);

    int hitPointsRecovered = character.getStats().getMaxHitPoints().total() - character.getStats().getHitPoints();
    if (!isLongRest)
    {
      hitPointsRecovered = roll.getRecoveredHitPoints();
    }
    RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(character, hitPointsRecovered);
    CommandDelegate.execute(cmd);

    int manaRecovered = character.getStats().getMaxMana().total() - character.getStats().getMana();
    if (!isLongRest)
    {
      manaRecovered = 1;
    }
    RecoverManaCmd mana = new RecoverManaCmd(character, manaRecovered);
    CommandDelegate.execute(mana);

    soberUp();

    SendMessageCmd echo = new SendMessageCmd(chatId, "<b>You recovered ❤" + hitPointsRecovered +
            " and ✨" + manaRecovered + ".</b> " +
            "Now at ❤" + character.getStats().getHitPoints() + "/" + character.getStats().getMaxHitPoints() +
            ", ✨" + character.getStats().getMana() + "/" + character.getStats().getMaxMana());
    CommandDelegate.execute(echo);

    MoveToLocationCmd chrisProtector = new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(chrisProtector);
  }

  private void soberUp()
  {
    for (int i = 0; i < 3; i++)
    {
      SoberUpCmd sober = new SoberUpCmd(character, chatId);
      CommandDelegate.execute(sober);
    }
  }
}
