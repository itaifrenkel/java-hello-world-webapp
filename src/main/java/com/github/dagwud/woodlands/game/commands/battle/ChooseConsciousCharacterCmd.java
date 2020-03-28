package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.FightingGroup;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class ChooseConsciousCharacterCmd extends AbstractCmd
{
  private final FightingGroup party;
  private GameCharacter chosen = null;

  public ChooseConsciousCharacterCmd(FightingGroup party)
  {
    this.party = party;
  }

  @Override
  public void execute()
  {
    for (Fighter member : party.getActiveMembers())
    {
      if (member instanceof GameCharacter && member.isConscious())
      {
        chosen = (GameCharacter) member;
        return;
      }
    }
  }

  public GameCharacter getChosen()
  {
    return chosen;
  }
}
