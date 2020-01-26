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
  private Map<GameCharacter, Integer> buffs;

  public AirOfAuthority(General caster)
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
      int buffAmount = 1;
      target.getStats().setDefenceRating(target.getStats().getDefenceRating() + buffAmount);
      buffs.put(target, buffAmount);

      SendMessageCmd cmd = new SendMessageCmd(target.getPlayedBy().getChatId(), getCaster().getName() + " buffed your defences by +" + buffAmount);
      CommandDelegate.execute(cmd);
    }
  }

  @Override
  public void expire()
  {
    for (GameCharacter target : buffs.keySet())
    {
      Integer buffedAmount = buffs.get(target);
      target.getStats().setDefenceRating(target.getStats().getDefenceRating() - buffAmount);
    }
  }

  @Override
  GameCharacter getCaster()
  {
    return (GameCharacter) super.getCaster();
  }
}
