package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public class ArcaneInspiration extends PassivePartySpell
{
  private static final long serialVersionUID = 1L;

  private static final int MANA_BOOST = 1;
  private Map<PlayerCharacter, Integer> boosted;

  public ArcaneInspiration(PlayerCharacter caster)
  {
    super("Arcane Inspiration", caster);
    boosted = new HashMap<>();
  }

  @Override
  public boolean cast()
  {
    for (PlayerCharacter target : getCaster().getParty().getActivePlayerCharacters())
    {
      target.getStats().getMaxMana().addBonus(MANA_BOOST);
      boosted.put(target, MANA_BOOST);

      if (target != getCaster())
      {
        SendMessageCmd cmd = new SendMessageCmd(target,
                getCaster().getName() + " is boosting your maximum mana by +" + MANA_BOOST);
        CommandDelegate.execute(cmd);
      }
    }
    return true;
  }

  @Override
  public void expire()
  {
    for (PlayerCharacter character : boosted.keySet())
    {
      character.getStats().getMaxMana().removeBonus(MANA_BOOST);
      if (character.getStats().getMana() > character.getStats().getMaxMana().getBase())
      {
        character.getStats().setMana(character.getStats().getMaxMana().getBase());
      }

      SendMessageCmd cmd = new SendMessageCmd(character,
              getCaster().getName() + " is no longer boosting your maximum mana");
      CommandDelegate.execute(cmd);
    }
    boosted.clear();
  }
}
