package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.creatures.SpawnCreatureByDifficultyCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.ArrayList;
import java.util.List;

public abstract class GenerateEncounterCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerState playerState;
  private final ELocation location;
  private final int enemyCount;
  private final double minDifficulty;
  private final double maxDifficulty;
  private String creatureType;

  GenerateEncounterCmd(PlayerState playerState, ELocation location, int enemyCount, double minDifficulty, double maxDifficulty, String creatureType)
  {
    this.playerState = playerState;
    this.location = location;
    this.enemyCount = enemyCount;
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
      Party party = playerState.getActiveCharacter().getParty();
      if (!party.canAct())
      {
        return;
      }

      // There's already an encounter in progress; don't start another one:
      if (party.getActiveEncounter() != null)
      {
        if (party.getActiveEncounter().isEnded())
        {
          party.setActiveEncounter(null);
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
      party.setActiveEncounter(encounter);

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
    String message = buildEncounteredSummary(encounter);

    SendPartyMessageCmd msg = new SendPartyMessageCmd(playerState.getActiveCharacter().getParty(), message);
    CommandDelegate.execute(msg);
    return encounter;
  }

  private String buildEncounteredSummary(Encounter encounter)
  {
    StringBuilder b = new StringBuilder();
    b.append(encounter.getEnemies().size() == 1 ? "<b>You encountered a </b>" : "<b>You encountered:</b>\n");
    for (Creature enemy : encounter.getEnemies())
    {
      if (encounter.getEnemies().size() > 1)
      {
        b.append("• ");
      }
      b.append(buildEnemySummary(enemy));
      if (encounter.getEnemies().size() > 1)
      {
        b.append("");
      }
      b.append(enemy.getCarrying().summary(enemy));
      if (encounter.getEnemies().size() > 1)
      {
        b.append("\n");
      }
    }
    return b.toString();
  }

  private String buildEnemySummary(Creature enemy)
  {
    return "<b>" + enemy.name + " (L" + enemy.difficulty() + "):</b>\n" + enemy.summary();
  }

  private Encounter createEncounter()
  {
    List<Creature> creatures = new ArrayList<>();
    for (int i = 0; i < enemyCount; i++)
    {
      SpawnCreatureByDifficultyCmd cmd = new SpawnCreatureByDifficultyCmd(getMinDifficulty(), getMaxDifficulty(), getCreatureType());
      CommandDelegate.execute(cmd);
      creatures.add(cmd.getSpawnedCreature());
    }
    return createEncounter(getPlayerState().getActiveCharacter().getParty(), creatures);
  }

  abstract Encounter createEncounter(Party party, List<Creature> enemy);

  protected final PlayerState getPlayerState()
  {
    return playerState;
  }

  protected final ELocation getLocation()
  {
    return location;
  }

  protected final double getMinDifficulty()
  {
    return minDifficulty;
  }

  protected final double getMaxDifficulty()
  {
    return maxDifficulty;
  }

  final String getCreatureType()
  {
    return creatureType;
  }

  public int getEnemyCount()
  {
    return enemyCount;
  }
}
