package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.domain.Encounter;

public class EncounterNotEndedPrerequisite implements CommandPrerequisite
{
  private final Encounter encounter;

  public EncounterNotEndedPrerequisite(Encounter encounter)
  {
    this.encounter = encounter;
  }

  @Override
  public boolean verify()
  {
    return !encounter.isEnded();
  }
}
