package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.LevelUpCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

import java.util.logging.Level;

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
    if (encounter != null)
    {
      encounter.end();
      encounter.getParty().setActiveEncounter(null);
    }
  }
}
