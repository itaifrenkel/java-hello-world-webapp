package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.ShowCharacterInfoCmd;
import com.github.dagwud.woodlands.game.commands.battle.DeathCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.InventoryCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ClearCraftersCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public ClearCraftersCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), character);
  }

  @Override
  protected void execute()
  {
    character.getParty().getBlacksmith().setCrafting(false);
    character.getParty().getAlchemist().setCrafting(false);
  }
}
