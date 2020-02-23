package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.creatures.SpawnCreatureCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;

public abstract class GenerateEncounterCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerState playerState;
  private final ELocation location;
  private final double minDifficulty;
  private final double maxDifficulty;
  private String creatureType;

  GenerateEncounterCmd(PlayerState playerState, ELocation location, double minDifficulty, double maxDifficulty, String creatureType)
  {
    this.playerState = playerState;
    this.location = location;
    this.minDifficulty = minDifficulty;
    this.maxDifficulty = maxDifficulty;
    this.creatureType = creatureType;
  }

  @Override
  public final void execute()
  {
    {
      // You've left the location of the battles; encounter no longer happens:
      if (playerState.getActiveCharacter().getLocation() != location)
      {
        return;
      }

      // Party is all dead:
      if (!playerState.getActiveCharacter().getParty().canAct())
      {
        return;
      }

      // There's already an encounter in progress; don't start another one:
      if (playerState.getActiveCharacter().getParty().getActiveEncounter() != null)
      {
        if (playerState.getActiveCharacter().getParty().getActiveEncounter().isEnded())
        {
          playerState.getActiveCharacter().getParty().setActiveEncounter(null);
        }
        else
        {
          scheduleNextEncounter();
          return;
        }
      }

      boolean shouldHaveEncounter = shouldHaveEncounter();
      if (!shouldHaveEncounter)
      {
        String msg = "<i>Time passes. You keep moving. Nothing interesting happens.</i>";
        if (Math.random() <= 0.1d)
        {
          msg = "<i>You see some trees.</i>";
        }
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(playerState.getPlayer().getActiveCharacter().getParty(), msg);
        CommandDelegate.execute(cmd);
        scheduleNextEncounter();
        return;
      }

      Encounter encounter = startEncounter();
      playerState.getActiveCharacter().getParty().setActiveEncounter(encounter);

      scheduleFirstRound(encounter);
      scheduleNextEncounter();
    }
  }

  protected abstract void scheduleFirstRound(Encounter encounter);

  protected abstract boolean shouldHaveEncounter();

  protected abstract void scheduleNextEncounter();

  private Encounter startEncounter()
  {
    Encounter encounter = createEncounter();
    String message = "<b>You encountered a " + encounter.getEnemy().name + " (L" + encounter.getEnemy().difficulty + "):</b>\n" + encounter.getEnemy().summary();
    Item carriedLeft = encounter.getEnemy().getCarrying().getCarriedLeft();
    Item carriedRight = encounter.getEnemy().getCarrying().getCarriedRight();
    if (carriedLeft != null || carriedRight != null)
    {
      if (carriedLeft != null)
      {
        message += ", ";
        message += carriedLeft.summary(encounter.getEnemy(), false);
      }
      if (carriedRight != null)
      {
        message += ", ";
        message += carriedRight.summary(encounter.getEnemy(), false);
      }
    }

    SendPartyMessageCmd msg = new SendPartyMessageCmd(playerState.getActiveCharacter().getParty(), message);
    CommandDelegate.execute(msg);
    return encounter;
  }

  private Encounter createEncounter()
  {
    SpawnCreatureCmd cmd = new SpawnCreatureCmd(getMinDifficulty(), getMaxDifficulty(), getCreatureType());
    CommandDelegate.execute(cmd);
    Creature creature = cmd.getSpawnedCreature();
    return createEncounter(getPlayerState().getActiveCharacter().getParty(), creature);
  }

  abstract Encounter createEncounter(Party party, Creature enemy);

  protected final PlayerState getPlayerState()
  {
    return playerState;
  }

  protected final ELocation getLocation()
  {
    return location;
  }

  final double getMinDifficulty()
  {
    return minDifficulty;
  }

  final double getMaxDifficulty()
  {
    return maxDifficulty;
  }

  final String getCreatureType()
  {
    return creatureType;
  }
}
