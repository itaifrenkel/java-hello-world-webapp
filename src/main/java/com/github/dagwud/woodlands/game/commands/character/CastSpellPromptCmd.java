package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.*;

public class CastSpellPromptCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  public CastSpellPromptCmd(int chatId, GameCharacter character)
  {
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

  private SingleCastSpell prepareSpell(GameCharacter character)
  {
    switch (character.getCharacterClass())
    {
      case DRUID:
        return new FogOfConfusion(character);
      case TRICKSTER:
        return new SneakAttack(character);
      default:
        SendMessageCmd cmd = new SendMessageCmd(chatId, "No spells are available for " + character.getCharacterClass() + " (yet))");
        CommandDelegate.execute(cmd);
        throw new WoodlandsRuntimeException("Unsupported");
    }
  }
}