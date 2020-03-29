package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.FreeTextCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class EmoteCmd extends AbstractCmd implements FreeTextCmd
{
  private String text;
  private PlayerCharacter character;

  public EmoteCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    String message = character.getName() + " " + text;

    CommandDelegate.execute(new SendLocationMessageCmd(character.getLocation(), message));
  }

  @Override
  public void setFreeText(String text)
  {
    this.text = text;
  }
}
