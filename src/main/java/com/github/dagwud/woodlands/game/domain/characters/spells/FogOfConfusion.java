package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class FogOfConfusion extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;
  private static final int HIT_CHANCE_PENALTY = 10000;
  private Fighter target;

  public FogOfConfusion(PlayerCharacter caster)
  {
    super("Fog of Confusion", caster);
  }

  @Override
  public boolean cast()
  {
    target = getCaster().getParty().getActiveEncounter().chooseFighterToAttack(getCaster());
    target.getStats().setHitBoost(target.getStats().getHitBoost() - HIT_CHANCE_PENALTY);
    if (target instanceof PlayerCharacter)
    {
      SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
              getCaster().getName() + " reduced your hit accuracy by -" + HIT_CHANCE_PENALTY);
      CommandDelegate.execute(cmd);
    }
    return true;
  }

  @Override
  public void expire()
  {
    target.getStats().setHitBoost(target.getStats().getHitBoost() + HIT_CHANCE_PENALTY);
    if (target instanceof PlayerCharacter)
    {
      SendMessageCmd cmd = new SendMessageCmd(((PlayerCharacter) target).getPlayedBy().getChatId(),
              getCaster().getName() + " is no longer reducing your hit accuracy by -" + HIT_CHANCE_PENALTY);
      CommandDelegate.execute(cmd);
    }
  }

  @Override
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }
}
