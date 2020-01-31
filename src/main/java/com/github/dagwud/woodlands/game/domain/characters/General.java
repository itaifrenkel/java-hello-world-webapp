package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.AirOfAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class General extends PlayerCharacter
{
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

  public int countActivePeasants()
  {
    return (int) peasants.stream().filter(NonPlayerCharacter::isActive).count();
  }
}
