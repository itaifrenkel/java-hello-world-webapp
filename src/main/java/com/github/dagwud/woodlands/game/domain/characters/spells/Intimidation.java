package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.characters.Brawler;

import java.util.HashMap;
import java.util.Map;

public class Intimidation extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  private Map<GameCharacter, Integer> buffs;

  public Intimidation(Brawler caster)
  {
    super("Intimidation", caster);
    buffs = new HashMap<>();
  }

  @Override
  public boolean cast()
  {
    int boost = 20; // equivalent of a natural d20 - guaranteed to cause a critical hit
    for (GameCharacter member : getCaster().getParty().getActiveMembers())
    {
      member.getStats().setCriticalStrikeChanceBonus(member.getStats().getCriticalStrikeChanceBonus() + boost);
      buffs.put(member, boost);
    }
    return true;
  }

  @Override
  public void expire()
  {
    for (GameCharacter target : buffs.keySet())
    {
      Integer buffedAmount = buffs.get(target);
      target.getStats().setCriticalStrikeChanceBonus(target.getStats().getHitBoost() + buffedAmount);
    }
    buffs.clear();
  }

  @Override
  public Brawler getCaster()
  {
    return (Brawler) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }

}
