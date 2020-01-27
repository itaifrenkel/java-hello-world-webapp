package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Peasant;

import java.util.Collection;

public class ArmyOfPeasants extends SingleCastSpell
{
  Collection<Fighter> peasants;

  public ArmyOfPeasants(PlayerCharacter character)
  {
    super("Army of Peasants", character);
  }

  @Override
  public void cast()
  {
    for (int i = 0; i < determineNumberOfPeasants(); i++)
    {
      String name = "Peasant #" + (i + 1) + " (" + getCaster().getName() + ")";
      GameCharacter peasant = new Peasant(getCaster().getPlayedBy(), 1, name);
      JoinPartyCmd cmd = new JoinPartyCmd(peasant, getCaster().getParty().getName());
      CommandDelegate.execute(cmd);
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
  PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }
}
