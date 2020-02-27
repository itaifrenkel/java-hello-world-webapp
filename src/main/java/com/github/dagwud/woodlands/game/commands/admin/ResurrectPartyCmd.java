package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.FullHealCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.IsAdminPrerequisite;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ResurrectPartyCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter character;
  private final int chatId;

  public ResurrectPartyCmd(int chatId, PlayerCharacter character)
  {
    super(new IsAdminPrerequisite(chatId));
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    for (PlayerCharacter character : character.getParty().getActivePlayerCharacters())
    {
      CommandDelegate.execute(new FullHealCmd(chatId, character));
      CommandDelegate.execute(new SendMessageCmd(chatId, "Resurrected " + character.getName()));
    }
  }
}
