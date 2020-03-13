package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.*;

import java.util.ArrayList;
import java.util.Collection;

public class Pimp extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  private Collection<Ho> hos;

  Pimp(Player playedBy)
  {
    super(playedBy, ECharacterClass.PIMP);

    getSpellAbilities().register(new PimpSlap(this)); // passive
    getSpellAbilities().register(new EntourageOfHoes(this)); // active
    getSpellAbilities().register(new CallToTwerk(this)); // active

    hos = new ArrayList<>(4);
  }

  public Collection<Ho> getHos()
  {
    return hos;
  }

  public int countAliveHos()
  {
    int count = 0;
    for (Ho ho : hos)
    {
      if (!ho.isDead())
      {
        count++;
      }
    }
    return count;
  }

  public void clearDeadPeasants()
  {
    hos.removeIf(Fighter::isDead);
  }
}
