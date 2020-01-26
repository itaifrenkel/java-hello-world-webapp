package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

public class RecoverHitPointsCmd extends AbstractCmd
{
  private final Fighter target;
  private final int hitPointsRecovered;

  public RecoverHitPointsCmd(Fighter target, int hitPointsRecovered)
  {
    this.target = target;
    this.hitPointsRecovered = hitPointsRecovered;
  }

  @Override
  public void execute()
  {
    target.getStats().setHitPoints(target.getStats().getHitPoints() + hitPointsRecovered);
    if (target.getStats().getHitPoints() > 0)
    {
      target.getStats().setState(EState.ALIVE);

      for (PartySpell partySpell : target.getSpellAbilities().getPartySpells())
      {
        partySpell.cast();
      }
    }
  }
}
