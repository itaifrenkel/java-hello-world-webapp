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
      if (member instanceof PlayerCharacter)
      {
        SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) member).getPlayedBy().getChatId(),
                getCaster().getName() + " is intimidating the enemy; you're more likely to land a critical hit");
        CommandDelegate.execute(cmd);
      }
    }
    return true;
  }

  @Override
  public void expire()
  {
    for (GameCharacter target : buffs.keySet())
    {
      Integer buffedAmount = buffs.get(target);
      target.getStats().setCriticalStrikeChanceBonus(target.getStats().getCriticalStrikeChanceBonus() - buffedAmount);
      if (target instanceof PlayerCharacter)
      {
        SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
                getCaster().getName() + " is no longer intimidating the enemy");
        CommandDelegate.execute(cmd);
      }
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
