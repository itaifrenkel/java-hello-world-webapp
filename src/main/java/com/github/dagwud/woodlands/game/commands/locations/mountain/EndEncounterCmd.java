package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;

public class EndEncounterCmd extends AbstractCmd
{
  private final Encounter encounter;

  public EndEncounterCmd(Encounter encounter)
  {
    this.encounter = encounter;
  }

  @Override
  public void execute()
  {
    encounter.end();
    encounter.getParty().setActiveEncounter(null);
  }
}
