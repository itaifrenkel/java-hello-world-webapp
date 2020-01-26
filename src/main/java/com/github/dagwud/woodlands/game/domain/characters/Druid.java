package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.BattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

import java.util.Collection;
import java.util.Collections;

public class Druid extends GameCharacter
{
  Druid(Player playedBy)
  {
    super(playedBy, ECharacterClass.DRUID);
  }

  @Override
  public Collection<BattleRoundSpell> getPassives()
  {
    return Collections.emptyList();
  }

  @Override
  public Collection<PartySpell> getPartySpells()
  {
    return Collections.emptyList();
  }
}
