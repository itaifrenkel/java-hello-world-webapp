package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.character.BeginHerbalRemedyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RollShortRestCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class HerbalRemedy extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;
  private GameCharacter target;

  public HerbalRemedy(PlayerCharacter caster)
  {
    super("Herbal Remedy", caster);
  }

  public HerbalRemedy(PlayerCharacter caster, GameCharacter target)
  {
    super("Herbal Remedy", caster);
    this.target = target;
  }

  @Override
  public boolean cast()
  {
    if (target == null)
    {
      CommandDelegate.execute(new BeginHerbalRemedyCmd(getCaster().getPlayedBy().getPlayerState()));
      return false;
    }
    else
    {
      if (target.getLocation() == getCaster().getLocation() && !target.isDead())
      {
        RollShortRestCmd roll = new RollShortRestCmd(target);
        CommandDelegate.execute(roll);
        if (roll.getRecoveredHitPoints() != 0)
        {
          RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(target, roll.getRecoveredHitPoints());
          CommandDelegate.execute(cmd);
          CommandDelegate.execute(new SendMessageCmd(getCaster().getPlayedBy().getChatId(), getCaster().getName() + " passes some mystical, smoking tube to " + target.getName() + " who mystically inhales deeply and recovers " + roll.getRecoveredHitPoints() + " mystical points. Aweh"));
        }
        else
        {
          CommandDelegate.execute(new SendMessageCmd(getCaster().getPlayedBy().getChatId(), getCaster().getName() + " passes some mystical, smoking tube to " + target.getName() + " who mystically inhales deeply, then coughs awkwardly and turns green. That didn't, like, help bro. Aweh."));
        }
      }
    }

    return true;
  }



  @Override
  public boolean canCastOutsideOfBattle()
  {
    return true;
  }

  @Override
  public void expire()
  {
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
