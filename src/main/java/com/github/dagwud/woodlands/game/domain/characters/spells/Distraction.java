package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.Trickster;

import java.util.HashMap;
import java.util.Map;

public class Distraction extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  private Map<Fighter, Integer> debuffs;

  public Distraction(Trickster caster)
  {
    super("Distraction", caster);
    debuffs = new HashMap<>();
  }

  @Override
  public boolean cast()
  {
    Fighter target = getCaster().getParty().getActiveEncounter().chooseFighterToAttack(getCaster());
    if (target == null)
    {
      return false;
    }

    int hitBoost = target.getStats().getHitBoost();

    int change = (int) Math.min(Math.floor((double)getCaster().getStats().getLevel() / 3.0), 7);

    debuffs.put(target, change);

    target.getStats().setHitBoost(hitBoost - change);

    return true;
  }

  @Override
  public void expire()
  {
    for (Fighter target : debuffs.keySet())
    {
      Integer buffedAmount = debuffs.get(target);
      target.getStats().setHitBoost(target.getStats().getHitBoost() + buffedAmount);
    }
  }

  @Override
  public Trickster getCaster()
  {
    return (Trickster) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }

}
