package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.*;

import java.util.ArrayList;
import java.util.List;

public class ManualEncounterRoundCmd extends EncounterRoundCmd
{
  public ManualEncounterRoundCmd(PlayerState playerState, int delayBetweenRoundsMs)
  {
    super(playerState, delayBetweenRoundsMs);
  }

  @Override
  void executePreparationPhase(Encounter encounter)
  {
    ManualEncounter manualEncounter = (ManualEncounter) encounter;

    for (FightingGroup group : encounter.getAllFightingGroups())
    {
      for (PlayerCharacter character : group.getActivePlayerCharacters())
      {
        RequestEncounterRoundPlanningCmd planning = new RequestEncounterRoundPlanningCmd(character.getPlayedBy().getPlayerState(), manualEncounter);
        CommandDelegate.execute(planning);
      }
    }
  }

  @Override
  protected void scheduleFightPhase(Encounter encounter, PlayerState playerState)
  {
    ManualEncounter manualEncounter = (ManualEncounter) playerState.getActiveCharacter().getParty().getActiveEncounter();
    if (manualEncounter == null)
    {
      // encounter had ended
      return;
    }

    EncounterRoundFightCmd fight = new EncounterRoundFightCmd(playerState, encounter, getDelayBetweenRoundsMS());
    RunLaterCmd startRound = new RunLaterCmd(manualEncounter.getTimeAllowedForPlanningMS(), fight);
    CommandDelegate.execute(startRound);
  }
}
