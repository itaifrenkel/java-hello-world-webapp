package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

import java.util.HashMap;
import java.util.Map;

public class ArcaneInspiration extends PartySpell
{
  private static final int MANA_BOOST = 1;
  private Map<GameCharacter, Integer> boosted;

  public ArcaneInspiration(GameCharacter caster)
  {
    super("Arcane Inspiration", caster);
    boosted = new HashMap<>();
  }

  @Override
  public void cast()
  {
    for (GameCharacter target : getCaster().getParty().getActiveMembers())
    {
      int initial = getCaster().getStats().getMaxMana();
      target.getStats().setMaxMana(initial + MANA_BOOST);
      boosted.put(target, MANA_BOOST);

      if (target != getCaster())
      {
        SendMessageCmd cmd = new SendMessageCmd(target.getPlayedBy().getChatId(),
                getCaster().getName() + " is boosting your maximum mana by +" + MANA_BOOST);
        CommandDelegate.execute(cmd);
      }
    }
  }

  @Override
  public void expire()
  {
    for (GameCharacter character : boosted.keySet())
    {
      character.getStats().setMaxMana(character.getStats().getMaxMana() - MANA_BOOST);
      if (character.getStats().getMana() > character.getStats().getMaxMana())
      {
        character.getStats().setMana(character.getStats().getMaxMana());
      }

      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(),
              getCaster().getName() + " is no longer boosting your maximum mana");
      CommandDelegate.execute(cmd);
    }
  }
}
