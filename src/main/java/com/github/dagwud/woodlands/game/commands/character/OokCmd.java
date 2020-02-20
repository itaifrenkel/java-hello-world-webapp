package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class OokCmd extends AbstractCmd
{
  private PlayerCharacter playerCharacter;

  public OokCmd(PlayerCharacter playerCharacter)
  {
    this.playerCharacter = playerCharacter;
  }

  @Override
  public void execute()
  {
    new SendMessageCmd(playerCharacter.getPlayedBy().getChatId(), "Ook! Ook ook! Did you mean /look?").go();
  }
}
