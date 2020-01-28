package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class FogOfConfusion extends SingleCastSpell
{
  private static final int HIT_CHANCE_PENALTY = 10000;
  private Creature target;

  public FogOfConfusion(PlayerCharacter caster)
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
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 0;
  }
}
