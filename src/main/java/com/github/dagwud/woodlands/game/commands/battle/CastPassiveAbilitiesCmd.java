package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.CastSpellCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassiveBattleRoundSpell;

import java.util.ArrayList;
import java.util.List;

public class CastPassiveAbilitiesCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final List<Fighter> fighters;
  private List<PassiveBattleRoundSpell> passivesCast;

  public CastPassiveAbilitiesCmd(List<Fighter> fighters)
  {
    this.fighters = fighters;
  }

  @Override
  public void execute()
  {
    passivesCast = new ArrayList<>();
    for (Fighter member : fighters)
    {
      if (member.isConscious())
      {
        passivesCast.addAll(member.getSpellAbilities().getPassives());
      }
    }

    passivesCast.removeIf(p -> !p.shouldCast());

    for (PassiveBattleRoundSpell spellToCast : passivesCast)
    {
      CommandDelegate.execute(new CastSpellCmd(spellToCast));
    }
  }

  public List<PassiveBattleRoundSpell> getPassivesCast()
  {
    return passivesCast;
  }
}
