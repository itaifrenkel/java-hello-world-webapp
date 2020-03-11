package com.github.dagwud.woodlands.game.commands.locations.sparringtent;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateManualEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.*;

import java.util.Collections;
import java.util.List;

public class GenerateSparringEncounterCmd extends GenerateManualEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateSparringEncounterCmd(PlayerState host)
  {
    super(host, ELocation.SPARRING_TENT, 0, -1, -1, null, Settings.SPARRING_TENT_TIME_ALLOWED_FOR_PLANNING_MS, Settings.SPARRING_TENT_ACTIONS_PER_ROUND);
  }

  @Override
  protected boolean shouldHaveEncounter()
  {
    return true;
  }

  @Override
  protected List<Fighter> produceEnemies()
  {
    PlayerCharacter partner = findSparringPartnerIfAny(getPlayerState().getActiveCharacter());
    return Collections.singletonList(partner);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    CommandDelegate.execute(new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateSparringEncounterCmd(getPlayerState())));
  }

  private PlayerCharacter findSparringPartnerIfAny(PlayerCharacter partnerFor)
  {
    List<PlayerCharacter> partners = partnerFor.getParty().findPlayerCharactersIn(ELocation.SPARRING_TENT);
    partners.remove(partnerFor);

    if (partners.isEmpty())
    {
      return null;
    }

    return partners.get(0);
  }

  @Override
  protected Encounter createEncounter(Party party, List<? extends Fighter> enemy)
  {
    return new SparringEncounter(party, getPlayerState().getActiveCharacter(), produceEnemies(), getTimeAllowedForPlanningMS(), getActionsPerRound());
  }

  @Override
  protected String buildEncounteredSummary(Encounter encounter)
  {
    StringBuilder b = new StringBuilder();
    for (Fighter fighter : encounter.getAllFighters())
    {
      if (b.length() == 0)
      {
        b.append("<b>Sparring Match: ");
      }
      else
      {
        b.append(" vs ");
      }
      b.append(fighter.getName());
    }
    b.append("</b>\n");
    for (Fighter enemy : encounter.getAllFighters())
    {
      b.append("• ");
      b.append(buildEnemySummary(enemy));
      b.append(enemy.getCarrying().summary(enemy));
      b.append("\n");
    }
    return "<b>" + b.toString();
  }
}
