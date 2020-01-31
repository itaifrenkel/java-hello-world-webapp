package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Peasant;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;

public class ArmyOfPeasants extends SingleCastSpell
{
  public ArmyOfPeasants(PlayerCharacter character)
  {
    super("Army of Peasants", character);
  }

  @Override
  public void cast()
  {
    int peasantsAllowed = determineNumberOfPeasants();
    if (getCaster().countActivePeasants() >= peasantsAllowed)
    {
      SendMessageCmd msg = new SendMessageCmd(getCaster().getPlayedBy().getChatId(), "You don't command enough respoect to command more peasants");
      CommandDelegate.execute(msg);
      return;
    }

    for (int i = getCaster().countActivePeasants(); i < peasantsAllowed; i++)
    {
      String name = "Peasant #" + (i + 1) + " (" + getCaster().getName() + ")";
      Peasant peasant = new Peasant(getCaster().getPlayedBy(), 1, name);
      peasant.setLocation(getCaster().getLocation());
      JoinPartyCmd cmd = new JoinPartyCmd(peasant, getCaster().getParty().getName());
      CommandDelegate.execute(cmd);

      getCaster().getPeasants().add(peasant);
    }
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
    return 0;
  }
}
