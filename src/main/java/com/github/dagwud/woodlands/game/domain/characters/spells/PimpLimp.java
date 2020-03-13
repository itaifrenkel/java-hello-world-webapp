package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;

import java.util.HashMap;
import java.util.Map;

public class PimpLimp extends PassiveBattleRoundSpell
{
  private static final long serialVersionUID = 1L;

  private Map<Fighter, Integer> buffs;

  public PimpLimp(General caster)
  {
    super("Pimp Limp", caster);
    buffs = new HashMap<>();
  }

  @Override
  public boolean shouldCast()
  {
    return true;
  }

  @Override
  public boolean cast()
  {
    for (Fighter target : getCaster().getParty().getActiveMembers())
    {
      int buffAmount = rollBuff(target);
      if (buffAmount != 0)
      {
        target.getStats().getStrength().addBonus(buffAmount);
        buffs.put(target, buffAmount);

        if (target instanceof PlayerCharacter)
        {
          SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
                  getCaster().getName() + " pimped your strength by +" + buffAmount);
          CommandDelegate.execute(cmd);
        }
      }
    }
    return true;
  }

  @Override
  public void expire()
  {
    for (Fighter target : buffs.keySet())
    {
      Integer buffedAmount = buffs.get(target);
      target.getStats().getStrength().removeBonus(buffedAmount);
      if (target instanceof PlayerCharacter)
      {
        SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
                getCaster().getName() + " is no longer pimping your strength by +" + buffedAmount);
        CommandDelegate.execute(cmd);
      }
    }
    buffs.clear();
  }

  private int rollBuff(Fighter target)
  {
    if (getCaster() == target)
    {
      // Can't buff yourself:
      return 0;
    }

    int diceFaces = determineBuffDiceFaces(target);

    DiceRollCmd roll1 = new DiceRollCmd(1, diceFaces);
    CommandDelegate.execute(roll1);

    DiceRollCmd roll2 = new DiceRollCmd(1, diceFaces);
    CommandDelegate.execute(roll2);

    return Math.min(roll1.getTotal(), roll2.getTotal());
  }

  private int determineBuffDiceFaces(Fighter targetChar)
  {
    if (!(targetChar instanceof PlayerCharacter))
    {
      // NPCs don't get buffed:
      return 0;
    }
    PlayerCharacter target = (PlayerCharacter) targetChar;
    if (target.getCharacterClass() == ECharacterClass.TRICKSTER)
    {
      return 6;
    }
    if (target.getCharacterClass() == ECharacterClass.DRUID
            || target.getCharacterClass() == ECharacterClass.WIZARD
            || target.getCharacterClass() == ECharacterClass.EXPLORER)
    {
      return 4;
    }
    if (target.getCharacterClass() == ECharacterClass.BRAWLER)
    {
      return 8;
    }
    if (target.getCharacterClass() == ECharacterClass.GENERAL)
    {
      // Generals can't buff each other
      return 0;
    }
    return 0;
  }

  @Override
  public General getCaster()
  {
    return (General) super.getCaster();
  }

}
