package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.CastSpellCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassivePartySpell;

public class RecoverHitPointsCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Fighter target;
  private int hitPointsRecovered;

  public RecoverHitPointsCmd(Fighter target, int hitPointsRecovered)
  {
    this.target = target;
    this.hitPointsRecovered = hitPointsRecovered;
  }

  @Override
  public void execute()
  {
    int newHP = target.getStats().getHitPoints() + hitPointsRecovered;
    newHP = Math.min(newHP, target.getStats().getMaxHitPoints().total());
    hitPointsRecovered = newHP - target.getStats().getHitPoints();
    target.getStats().setHitPoints(newHP);
    if (target.getStats().getHitPoints() > 0)
    {
      if (target.getStats().getState() != EState.ALIVE)
      {
        for (PassivePartySpell passivePartySpell : target.getSpellAbilities().getPassivePartySpells())
        {
          CommandDelegate.execute(new CastSpellCmd(passivePartySpell));
        }
      }
      target.getStats().setState(EState.ALIVE);
    }
  }

  public int getHitPointsRecovered()
  {
    return hitPointsRecovered;
  }
}
