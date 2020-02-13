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
    patchTooManyItemsCarried();
    patchNewWizardSpell();
  }

  private void patchNewWizardSpell()
  {
    if (character.getCharacterClass() != ECharacterClass.WIZARD)
    {
      return;
    }
    for (SingleCastSpell s : character.getSpellAbilities().getKnownActiveSpell())
    {
      if (s instanceof HealingBlast)
      {
        return;
      }
    }
    character.getSpellAbilities().register(new HealingBlast(character));
    CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched wizard " + character + " with healing spell"));
  }

  private void patchRestingPlayers()
  {
    if (character.getStats().getState() == EState.RESTING)
    {
      character.getStats().setState(EState.ALIVE);
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: un-rested " + character.getName()));
    }
  }

  // only necessary to run once - on 11/12 feb
  private void patchTooManyItemsCarried()
  {
    int dropCount = character.getCarrying().countTotalCarried() - character.determineMaxAllowedItems();
    for (int i = 0; i < dropCount; i++)
    {
      CommandDelegate.execute(new DropItemCmd(character, character.getPlayedBy().getChatId(), "0"));
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Patched: dropped from " + character.getName()));
    }
  }
}
