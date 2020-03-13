package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Ho;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.Pimp;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class EntourageOfHoes extends SingleCastSpell
{
  private static final long serialVersionUID = 1L;

  public EntourageOfHoes(PlayerCharacter character)
  {
    super("Entourage of Ho's", character);
  }

  @Override
  public boolean cast()
  {
    int peasantsAllowed = determineNumberOfPeasants();
    int summoned = 0;
    for (int i = getCaster().countAliveHos(); i < peasantsAllowed; i++)
    {
      String name = "Ho #" + (i + 1) + " (" + getCaster().getName() + ")";
      Ho ho = new Ho(getCaster().getPlayedBy(), 1, name);
      ho.setLocation(getCaster().getLocation());
      Weapon weapon = ItemsCacheFactory.instance().getCache().findWeapon("Chlamydia");
      ho.getCarrying().setCarriedLeft(weapon);

      JoinPartyCmd cmd = new JoinPartyCmd(ho, getCaster().getParty().getName());
      CommandDelegate.execute(cmd);

      getCaster().getHos().add(ho);

      summoned++;
    }

    if (summoned == 0)
    {
      SendMessageCmd msg = new SendMessageCmd(getCaster().getPlayedBy().getChatId(), "You don't command enough respect to summon more ho's");
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
  public Pimp getCaster()
  {
    return (Pimp) super.getCaster();
  }

  @Override
  public int getManaCost()
  {
    return 1;
  }
}
