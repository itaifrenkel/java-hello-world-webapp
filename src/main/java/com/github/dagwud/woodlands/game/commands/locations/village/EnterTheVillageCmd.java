package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.commands.FullHealCmd;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class EnterTheVillageCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public EnterTheVillageCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    if (character.getStats().getHitPoints() == 0)
    {
      RecoverHitPointsCmd cmd = new RecoverHitPointsCmd(character, 1);
      CommandDelegate.execute(cmd);

      SendMessageCmd reviveMessage = new SendMessageCmd(character.getPlayedBy().getChatId(), "You come to on the dusty floor of the Village Square. A small crowd is gathered around, giggling, as you drag yourself up. You should get some rest");
      CommandDelegate.execute(reviveMessage);
    }
  }
}
