package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
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
  private final int minDifficulty;
  private final int maxDifficulty;
  private String creatureType;

  public GenerateEncounterCmd(PlayerState playerState, ELocation location, int minDifficulty, int maxDifficulty, String creatureType)
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

      ChanceCalculatorCmd chance = new ChanceCalculatorCmd(playerState.getActiveCharacter().getParty().getPercentChanceOfEncounter());
      CommandDelegate.execute(chance);

      if (!chance.getResult())
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

      EncounterRoundCmd cmd = new EncounterRoundCmd(playerState.getPlayer().getChatId(), encounter, Settings.DELAY_BETWEEN_ROUNDS_MS);
      CommandDelegate.execute(cmd);

      scheduleNextEncounter();
    }
  }

  private Encounter createEncounter(Party party)
  {
    SpawnCreatureCmd cmd = new SpawnCreatureCmd(minDifficulty, maxDifficulty, creatureType);
    CommandDelegate.execute(cmd);
    Creature creature = cmd.getSpawnedCreature();

    Encounter encounter = new Encounter();
    encounter.setParty(party);
    encounter.setEnemy(creature);

    return encounter;
  }
  protected abstract void scheduleNextEncounter();

  private Encounter startEncounter()
  {
    Encounter encounter = createEncounter(playerState.getActiveCharacter().getParty());

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

  protected PlayerState getPlayerState()
  {
    return playerState;
  }
}
