package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Peasant;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class ArmyOfPeasants extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  public ArmyOfPeasants(PlayerCharacter character)
  {
    super("Army of Peasants", character);
  }

  @Override
  public boolean cast()
  {
    int peasantsAllowed = determineNumberOfPeasants();
    int summoned = 0;
    for (int i = getCaster().countAlivePeasants(); i < peasantsAllowed; i++)
    {
      String name = "Peasant #" + (i + 1) + " (" + getCaster().getName() + ")";
      Peasant peasant = new Peasant(getCaster().getPlayedBy(), 1, name);
      peasant.setLocation(getCaster().getLocation());
      Weapon weapon = ItemsCacheFactory.instance().getCache().findWeapon("Pitchfork");
      peasant.getCarrying().setCarriedLeft(weapon);

      JoinPartyCmd cmd = new JoinPartyCmd(peasant, getCaster().getParty().getName());
      CommandDelegate.execute(cmd);

      getCaster().getPeasants().add(peasant);

      summoned++;
    }

    if (summoned == 0)
    {
      SendMessageCmd msg = new SendMessageCmd(getCaster().getPlayedBy().getChatId(), "You don't command enough respect to summon more peasants");
      CommandDelegate.execute(msg);
      return false;
    }
    return true;
  }

  private int determineNumberOfPeasants()
  {
    return Math.max(1, getCaster().getStats().getLevel() - 3);
  }

  @Override
  public void expire()
  {
  }

  @Override
  public General getCaster()
  {
    return (General) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }
}
