package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
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
  public Collection<? extends Spell> castPassives()
  {
    return Collections.emptyList();
  }
}
