package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class CastSpellCmd extends AbstractCmd
{
  private Spell spell;

  public CastSpellCmd(Spell spell)
  {
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    int mana = spell.getCaster().getStats().getMana();
    if (mana < spell.getManaCost())
    {
      if (spell.getCaster() instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter) spell.getCaster();
        SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You don't have enough mana to cast " + spell.buildSpellDescription());
        CommandDelegate.execute(cmd);
      }
      return;
    }
    spell.getCaster().getStats().setMana(mana);
    spell.cast();
  }
}
