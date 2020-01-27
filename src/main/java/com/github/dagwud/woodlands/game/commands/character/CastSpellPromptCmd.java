package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.FogOfConfusion;
import com.github.dagwud.woodlands.game.domain.characters.spells.PrepareSpellCmd;

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
    switch (character.getCharacterClass())
    {
      case DRUID:
        castDruid();
        break;
      default:
        SendMessageCmd cmd = new SendMessageCmd(chatId, "No spells are available for " + character.getCharacterClass() + " (yet))");
        CommandDelegate.execute(cmd);
    }
  }

  private void castDruid()
  {
    FogOfConfusion spell = new FogOfConfusion(character);
    PrepareSpellCmd cmd = new PrepareSpellCmd(character, spell);
    CommandDelegate.execute(cmd);

    SendMessageCmd send = new SendMessageCmd(chatId, "You will cast " + spell.getSpellName() + " on your next turn");
    CommandDelegate.execute(send);
  }
}
