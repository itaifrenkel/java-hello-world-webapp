package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.admin.AdminCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class FullHealCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter character;

  public FullHealCmd(int chatId, GameCharacter character)
  {
    super(chatId);
    this.character = character;
  }

  @Override
  public void execute()
  {
    character.getStats().setState(EState.ALIVE);

    int heal = character.getStats().getMaxHitPoints().total() - character.getStats().getHitPoints();
    RecoverHitPointsCmd hpCmd = new RecoverHitPointsCmd(character, heal);
    CommandDelegate.execute(hpCmd);

    int mana = character.getStats().getMaxMana().getBase() - character.getStats().getMana();
    RecoverManaCmd manaCmd = new RecoverManaCmd(character, mana);
    CommandDelegate.execute(manaCmd);

    character.getStats().setDrunkeness(0);
  }
}
