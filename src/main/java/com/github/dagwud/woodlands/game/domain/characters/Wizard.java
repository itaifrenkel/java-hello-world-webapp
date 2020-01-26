package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.BattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.util.Collection;
import java.util.Collections;

public class Wizard extends GameCharacter
{
  Wizard(Player playedBy)
  {
    super(playedBy, ECharacterClass.WIZARD);
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
