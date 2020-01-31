package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class CastSpellCmd extends AbstractCmd
{
  private Spell spell;

  public CastSpellCmd(Spell spell)
  {
    super(new AbleToActPrerequisite(spell.getCaster()));
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    if (spell.isCast())
    {
      System.err.println("WARNING: " + spell.getSpellName() + " has already been cast");
    }

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
    spell.setCast(true);
  }

  @Override
  public String toString()
  {
    return "CastSpellCmd{" +
            "spell=" + spell +
            '}';
  }
}
