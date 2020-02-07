package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.characters.Trickster;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.HashMap;
import java.util.Map;

public class Distraction extends PassiveBattleRoundSpell
{
  private static final long serialVersionUID = 1L;

  private Map<Creature, Integer> debuffs;

  public Distraction(Trickster caster)
  {
    super("Distraction", caster);
    debuffs = new HashMap<>();
  }

  @Override
  public boolean shouldCast()
  {
    return true;
  }

  @Override
  public boolean cast()
  {
// TODO HACK TO MAKE IT LESS OVERPOWERING UNTIL WE MAKE IT AN ACTIVE SPELL
if (Math.random() * 20 != 4) // a 1 in 20 chance
{
return false;
}
    Trickster caster = getCaster();
    Creature target = caster.getParty().getActiveEncounter().getEnemy();

    int hitBoost = target.getStats().getHitBoost();

    int change = (int) Math.min(Math.floor((double)caster.getStats().getLevel() / 3.0), 7);

    debuffs.put(target, change);

    target.getStats().setHitBoost(hitBoost - change);

    return true;
  }

  @Override
  public void expire()
  {
    for (Creature target : debuffs.keySet())
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

}
