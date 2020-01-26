package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

public class DeathCmd extends AbstractCmd
{
  private final Fighter target;

  public DeathCmd(Fighter target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
    target.getStats().setState(EState.DEAD);
    for (PartySpell partySpell : target.getSpellAbilities().getPartySpells())
    {
      partySpell.expire();
    }
  }
}
