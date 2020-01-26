package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;

import java.util.HashMap;
import java.util.Map;

public class AuraOfProtection extends BattleRoundSpell
{
  private static final int BUFF_AMOUNT = 20;

  private Map<GameCharacter, Integer> buffs;

  public AuraOfProtection(GameCharacter caster)
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
  public void cast()
  {
    for (GameCharacter target : getCaster().getParty().getActiveMembers())
    {
      target.getStats().setDefenceRating(target.getStats().getDefenceRating() + BUFF_AMOUNT);
      buffs.put(target, BUFF_AMOUNT);

      SendMessageCmd cmd = new SendMessageCmd(target.getPlayedBy().getChatId(), getCaster().getName() + " buffed your defences by +" + BUFF_AMOUNT);
      CommandDelegate.execute(cmd);
    }
  }

  @Override
  public void expire()
  {
    for (GameCharacter target : buffs.keySet())
    {
      Integer buffedAmount = buffs.get(target);
      target.getStats().setDefenceRating(target.getStats().getDefenceRating() - buffedAmount);
    }
  }

  @Override
  GameCharacter getCaster()
  {
    return (GameCharacter) super.getCaster();
  }
}
