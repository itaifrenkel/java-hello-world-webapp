package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PrepareSpellCmd;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import java.util.Collection;
import java.util.Iterator;

public class CastSpellPromptCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;

  public CastSpellPromptCmd(int chatId, PlayerCharacter character)
  {
    super(new AbleToActPrerequisite(character));
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    SingleCastSpell spell = prepareSpell(character);
    PrepareSpellCmd cmd = new PrepareSpellCmd(character, spell);
    CommandDelegate.execute(cmd);

    SendMessageCmd send = new SendMessageCmd(chatId, "You will cast " + spell.getSpellName() + " on your next turn");
    CommandDelegate.execute(send);
  }

  private SingleCastSpell prepareSpell(PlayerCharacter character)
  {
    Collection<SingleCastSpell> spellOptions = character.getSpellAbilities().getKnownActiveSpell();
    int toCastIndex = (int) (Math.random() * spellOptions.size());
    Iterator<SingleCastSpell> it = spellOptions.iterator();
    SingleCastSpell toCast = null;
    for (int i = 0; i <= toCastIndex; i++)
    {
      toCast = it.next();
    }
    return toCast;
  }
}