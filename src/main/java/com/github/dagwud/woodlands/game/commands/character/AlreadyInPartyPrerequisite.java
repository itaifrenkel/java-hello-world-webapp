package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class AlreadyInPartyPrerequisite implements CommandPrerequisite
{
  private final GameCharacter character;
  private final String partyName;

  AlreadyInPartyPrerequisite(GameCharacter character, String partyName)
  {
    this.character = character;
    this.partyName = partyName;
  }

  @Override
  public boolean verify()
  {
    if (alreadyInParty(character, partyName))
    {
      SendMessageCmd send = new SendMessageCmd(character.getPlayedBy().getChatId(), "You're already in that party!");
      CommandDelegate.execute(send);
      return false;
    }
    return true;
  }

  private boolean alreadyInParty(GameCharacter joiner, String partyNameToJoin)
  {
    return null != joiner.getParty() && joiner.getParty().getName().equals(partyNameToJoin);
  }

}
