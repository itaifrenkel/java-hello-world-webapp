package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class FullHealCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final GameCharacter character;

  public FullHealCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    int heal = character.getStats().getMaxHitPoints() - character.getStats().getHitPoints();
    RecoverHitPointsCmd hpCmd = new RecoverHitPointsCmd(character, heal);
    CommandDelegate.execute(hpCmd);

    if (character instanceof PlayerCharacter)
    {
      int mana = ((PlayerCharacter)character).getStats().getMaxMana().getBase() - character.getStats().getMana();
      RecoverManaCmd manaCmd = new RecoverManaCmd((PlayerCharacter)character, mana);
      CommandDelegate.execute(manaCmd);
    }
  }
}
