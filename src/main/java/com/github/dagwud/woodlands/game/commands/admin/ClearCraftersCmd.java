package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.domain.npc.Alchemist;
import com.github.dagwud.woodlands.game.domain.npc.Blacksmith;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.items.EquippableItem;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class ClearCraftersCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public ClearCraftersCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getChatId());
    this.character = character;
  }

  @Override
  public void execute()
  {
    for (PlayerCharacter c : character.getParty().getActivePlayerCharacters())
    {
      Blacksmith blacksmith = c.getParty().getBlacksmith();
      Weapon weapon = blacksmith.completeCrafting(c);
      if (weapon != null)
      {
        CommandDelegate.execute(new DoGiveItemCmd(null, blacksmith, weapon));
      }

      Alchemist alchemist = c.getParty().getAlchemist();
      EquippableItem equippableItem = alchemist.completeCrafting(c);
      if (equippableItem != null)
      {
        CommandDelegate.execute(new DoGiveItemCmd(null, alchemist, equippableItem));
      }
    }
  }
}
