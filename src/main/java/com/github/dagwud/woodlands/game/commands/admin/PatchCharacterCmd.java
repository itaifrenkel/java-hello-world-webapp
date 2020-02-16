package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.character.PeriodicSoberUpCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DropItemCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.HealingBlast;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import com.github.dagwud.woodlands.game.domain.trinkets.*;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.*;

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

    PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, chatId);
    CommandDelegate.execute(periodicSoberUp);
  }

  private void patchRestingPlayers()
  {
    if (character.isResting())
    {
      character.getStats().setState(EState.ALIVE);
      if (character.getStats().getState() == EState.SHORT_RESTING)
      {
        int rests = character.getStats().getRestPoints();
        rests = Math.max(0, rests + 1); // restore the short rest they were robbed of
        character.getStats().setRestPoints(rests);
      }
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: un-rested " + character.getName()));
    }

  }

}
