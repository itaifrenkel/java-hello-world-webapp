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
    patchConsumables();
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

    patchConsumables();
  }

  void patchConsumables()
  {
    for (Item item : character.getCarrying().getWorn())
    {
      patchConsumables(item);
    }

    for (Item item : character.getCarrying().getCarriedInactive())
    {
      patchConsumables(item);
    }
  }

  void patchConsumables(Item item)
  {
    if (item instanceof ConsumableTrinket)
    {
      ConsumableTrinket c = (ConsumableTrinket)item;
      ((Trinket)c).name = c.name;
      String pName = ((Trinket)item).name;
      String cName = c.name;
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: test " + pName + " vs " + cName));
    }
  }
}
