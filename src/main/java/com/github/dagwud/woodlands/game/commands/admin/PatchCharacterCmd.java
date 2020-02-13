package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DropItemCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.HealingBlast;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

public class PatchCharacterCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public PatchCharacterCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    patchRestingPlayers();
  }

  private void patchRestingPlayers()
  {
    if (character.getStats().getState() == EState.RESTING)
    {
      character.getStats().setState(EState.ALIVE);
      int rests = character.getStats().getRestPoints();
      rests = Math.max(0, rests + 1); // restore the short rest they were robbed of

      character.getStats().setRestPoints(rests);
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: un-rested " + character.getName()));
    }
if (character.playerChatId == 0)
{
  CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: linked " +  character.getName() + " to " + character.playerChatId));
}
else
{
  CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "not patched: " +  character.getName() + " is linked to " + character.playerChatId));
}
  }
}
