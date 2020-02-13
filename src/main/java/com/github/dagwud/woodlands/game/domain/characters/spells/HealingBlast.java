package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RollShortRestCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class HealingBlast extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  public HealingBlast(PlayerCharacter caster)
  {
    super("Healing Blast", caster);
  }

  @Override
  public boolean cast()
  {
    List<String> healed = new ArrayList<>();
    for (GameCharacter target : getCaster().getParty().getActiveMembers())
    {
      if (target.getLocation() == getCaster().getLocation() && !target.isDead())
      {
        RollShortRestCmd roll = new RollShortRestCmd(target);
        CommandDelegate.execute(roll);
        if (roll.getRecoveredHitPoints() != 0)
        {
          healed.add(target.getName() + " (❤" + roll.getRecoveredHitPoints() + ")");
        }
      }
    }

    String message = buildMessage(healed);
    SendPartyMessageCmd cmd = new SendPartyMessageCmd(getCaster().getParty(), message);
    CommandDelegate.execute(cmd);
    return true;
  }

  private String buildMessage(List<String> healed)
  {
    StringBuilder b = new StringBuilder("A wave ripples out from " + getCaster().getName() + ", healing ");
    for (int i = 0; i < healed.size(); i++)
    {
      if (i > 0 && i < healed.size() - 1)
      {
        b.append(", ");
      }
      b.append(healed.get(i));
      if (i == healed.size() - 2)
      {
        b.append(" and ");
      }
    }
    return b.toString();
  }

  @Override
  public void expire()
  {
    // nothing to do
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

  @Override
  public boolean canCastOutsideOfBattle()
  {
    return true;
  }
}
