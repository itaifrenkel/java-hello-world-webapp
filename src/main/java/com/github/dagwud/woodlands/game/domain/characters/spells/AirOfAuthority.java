package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.characters.GameCharacter;


import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

public class AirOfAuthority extends Spell
{
  private Map<GameCharacter, Integer> buffs;

  public AirOfAuthority(General caster)
  {
    super("Air of Authority", caster);
    buffs = new HashMap<>()(
  }

  @Override
  public boolean shouldCast()
  {
    return true;
   // BigDecimal PERCENT_CHANCE_PER_LEVEL = new BigDecimal("50"); //todo 5
  //  BigDecimal level = new BigDecimal(brawler.getStats().getLevel());
  //  ChanceCalculatorCmd cmd = new ChanceCalculatorCmd(PERCENT_CHANCE_PER_LEVEL.multiply(level));
  //  CommandDelegate.execute(cmd);
  //  return cmd.getResult();
  }

  @Override
  public void cast()
  {
    for (GameCharacter target : getCaster().getParty().getActiveMembers())
    {
      int buffAmt = rollBuff(target);
      buff.getStats().getStrength().addBonus(buffAmt);
      buffs.put(target, buffAmt);
    }
  }

  @Override
  public void expire()
  {
    for (GameCharacter target : buffs.keySet())
    {
      target.getStats().getStrength().removeBonus(buffs.get(target));
    }
  }

  private int rollBuff(GameCharacter target)
  {
    int dice = 0;
    switch (target.getCharacterClass())
    {
      case GENERAL:
        dice = 0;
        break;

      case TRICKSTER:
        dice = 6;
        break;

      case DRUID:
      case WIZARD:
      case EXPLORER:
        dice = 4;
        break;

      case BRAWLER:
        dice = 8;
        break;
    }

    DiceRollCmd cmd = new DiceRollCmd(1, dice);
    CommandDelegate.execute(cmd);
    return cmd.getTotal();
  }
}
