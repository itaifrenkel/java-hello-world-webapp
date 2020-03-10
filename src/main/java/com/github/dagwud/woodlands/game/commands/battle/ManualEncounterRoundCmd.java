package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.ManualEncounter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

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
    for (PlayerCharacter character : encounter.getAggressor().getActivePlayerCharacters())
    {
      RequestEncounterRoundPlanningCmd planning = new RequestEncounterRoundPlanningCmd(character.getPlayedBy().getPlayerState(), manualEncounter);
      CommandDelegate.execute(planning);
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
