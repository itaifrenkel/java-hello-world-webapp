package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;

public class EndEncounterCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Encounter encounter;

  public EndEncounterCmd(Encounter encounter)
  {
    this.encounter = encounter;
  }

  @Override
  public void execute()
  {
    if (encounter == null)
    {
      return;
    }

    encounter.end();
    encounter.getParty().setActiveEncounter(null);

    for (PlayerCharacter playerCharacter : encounter.getParty().getActivePlayerCharacters())
    {
      if (playerCharacter instanceof General)
      {
        General general = (General) playerCharacter;
        general.clearDeadPeasants();
      }
    }

    encounter.getParty().removeDeadNPCs();
  }
}
