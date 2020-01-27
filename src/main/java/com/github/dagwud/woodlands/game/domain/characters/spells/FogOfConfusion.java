package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class FogOfConfusion extends SingleCastSpell
{
  private static final int HIT_CHANCE_PENALTY = 10002;
  private Creature target;

  public FogOfConfusion(GameCharacter caster)
  {
    super("Fog of Confusion", caster);
  }

  @Override
  public void cast()
  {
    target = getCaster().getParty().getActiveEncounter().getEnemy();
    target.getStats().setHitBoost(target.getStats().getHitBoost() - HIT_CHANCE_PENALTY);
  }

  @Override
  public void expire()
  {
    target.getStats().setHitBoost(target.getStats().getHitBoost() + HIT_CHANCE_PENALTY);
  }

  @Override
  GameCharacter getCaster()
  {
    return (GameCharacter) super.getCaster();
  }
}
