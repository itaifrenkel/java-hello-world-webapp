package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.BeastMode;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.util.ArrayList;
import java.util.Collection;

public class Brawler extends GameCharacter
{
  Brawler(Player playedBy)
  {
    super(playedBy, ECharacterClass.BRAWLER);
  }

  @Override
  public Collection<? extends Spell> castPassives()
  {
    Collection<Spell> cast = new ArrayList<>();
    cast.add(new BeastMode(this));
    cast.removeIf(spellCast -> !spellCast.shouldCast());
    return cast;
  }

}
