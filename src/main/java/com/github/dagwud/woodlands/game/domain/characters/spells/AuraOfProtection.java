package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class AuraOfProtection extends PassiveBattleRoundSpell
{
  private static final long serialVersionUID = 1L;
  public static final int BUFF_AMOUNT = 1;

  private Map<GameCharacter, Integer> buffs;

  public AuraOfProtection(PlayerCharacter caster)
  {
    super("Aura of Protection", caster);
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
    for (GameCharacter target : getCaster().getParty().getActiveMembers())
    {
      if (target != getCaster())
      {
        target.getStats().setDefenceRatingBoost(target.getStats().getDefenceRatingBoost() + BUFF_AMOUNT);
        buffs.put(target, BUFF_AMOUNT);

        if (target instanceof PlayerCharacter)
        {
          SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
                  getCaster().getName() + " buffed your defences by +" + BUFF_AMOUNT);
          CommandDelegate.execute(cmd);
        }
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
      target.getStats().setDefenceRatingBoost(target.getStats().getDefenceRatingBoost() - buffedAmount);
 
      if (target instanceof PlayerCharacter)
      {
        SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
                getCaster().getName() + " is no longer buffing your defences by +" + BUFF_AMOUNT);
        CommandDelegate.execute(cmd);
      }
    }
    buffs.clear();
  }

  @Override
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }
}
