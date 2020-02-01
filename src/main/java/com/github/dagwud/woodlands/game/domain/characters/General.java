package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.LeavePartyCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.AirOfAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class General extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  private Collection<Peasant> peasants;

  General(Player playedBy)
  {
    super(playedBy, ECharacterClass.GENERAL);
    getSpellAbilities().register(new AirOfAuthority(this));
    peasants = new ArrayList<>(4);
  }

  public Collection<Peasant> getPeasants()
  {
    return peasants;
  }

  public int countAlivePeasants()
  {
    int count = 0;
    for (Peasant peasant : peasants)
    {
      if (!peasant.isDead())
      {
        count++;
      }
    }
    return count;
  }

  public void clearDeadPeasants()
  {
    for (Peasant peasant : peasants)
    {
      if (peasant.isDead())
      {
        LeavePartyCmd cmd = new LeavePartyCmd(peasant, getParty());
        CommandDelegate.execute(cmd);
      }
    }
    peasants.removeIf(Fighter::isDead);
  }
}
