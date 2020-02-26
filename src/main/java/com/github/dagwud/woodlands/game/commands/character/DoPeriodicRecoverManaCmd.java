package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.commands.RecoverManaCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class DoPeriodicRecoverManaCmd extends AbstractCmd
{
  private final PlayerCharacter character;
  private int manaRecovered;

  DoPeriodicRecoverManaCmd(PlayerCharacter character, int manaRecovered)
  {
    this.character = character;
    this.manaRecovered = manaRecovered;
  }

  @Override
  public void execute()
  {
    RecoverManaCmd recover = new RecoverManaCmd(character, manaRecovered);
    CommandDelegate.execute(recover);
    manaRecovered = recover.getManaRecovered();
    CommandDelegate.execute(new SendMessageCmd(character, "<b>You recovered " + Icons.MANA + manaRecovered + "</b>"));
  }
}
