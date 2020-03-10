package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.events.*;
import com.github.dagwud.woodlands.game.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EEvent
{
  PLAYER_DEATH, PLAYER_UNCONSCIOUS, JOINED_PARTY, LEFT_PARTY, MOVED, CREATURE_DROPPED_ITEM,
  CREATURE_DEFEATED, PLAYER_DROPPED_ITEM, PLAYER_GAVE_ITEM_AWAY, LEFT_ITEM, CRAFTED_ITEM, ENCHANTED_ITEM, CLAIMED_ITEM,
  LED_PARTY;

  private static transient Map<EEvent, List<EventRecipient<? extends Event>>> subscribers;

  public static void subscribeToStandardEvents()
  {
    EEvent.PLAYER_DEATH.subscribe(event -> CommandDelegate.execute(new SendPartyAlertCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getName() + " has died! Nice job, " + event.getPlayerCharacter().getParty().getLeader().getName())));
    EEvent.CLAIMED_ITEM.subscribe(new ClaimedItemEventRecipient());

    EEvent.JOINED_PARTY.subscribe(event ->
    {
      CommandDelegate.execute(new SendPartyAlertCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getName() + " has joined " + event.getPlayerCharacter().getParty().getName()));
      CommandDelegate.execute(new SendPartyMessageCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getName() + " has joined " + event.getPlayerCharacter().getParty().getName() + "!"));
    });

    EEvent.LEFT_PARTY.subscribe(event ->
    {
      CommandDelegate.execute(new SendPartyAlertCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getName() + " has left " + event.getPlayerCharacter().getParty().getName()));
      CommandDelegate.execute(new SendPartyMessageCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getName() + " has left " + event.getPlayerCharacter().getParty().getName()));
    });

    EEvent.MOVED.subscribe(event -> CommandDelegate.execute(new SendPartyAlertCmd(event.getPlayerCharacter().getParty(), event.getPlayerCharacter().getParty().getName() + " is entering " + event.getPlayerCharacter().getLocation().getDisplayName() + ".\nJoin the battle: @TheWoodlandsBot")));

    EEvent.CREATURE_DEFEATED.subscribe(new CreatureDefeatedEventRecipient());
    EEvent.CREATURE_DROPPED_ITEM.subscribe(new CreatureDroppedEventRecipient());

    ArmyOfPeasantsFleeRecipient armyOfPeasantsFleeRecipient = new ArmyOfPeasantsFleeRecipient();
    EEvent.PLAYER_DEATH.subscribe(armyOfPeasantsFleeRecipient);
    EEvent.PLAYER_UNCONSCIOUS.subscribe(armyOfPeasantsFleeRecipient);

    subscribeForAchievements();
  }

  private static void subscribeForAchievements()
  {
    EEvent.CREATURE_DROPPED_ITEM.subscribe(new CreatureWasMuggedEventRecipient());
    EEvent.CREATURE_DEFEATED.subscribe(new DrunkenVictoryEventRecipient());
    EEvent.PLAYER_DEATH.subscribe(new PlayerDeathAchievementEvent());
    EEvent.PLAYER_DROPPED_ITEM.subscribe(new CharacterDroppedItemEventRecipient());
    EEvent.PLAYER_GAVE_ITEM_AWAY.subscribe(new CharacterGaveItemEventRecipient());
    EEvent.LEFT_ITEM.subscribe(new CharacterLeftItemEventRecipient());

    EEvent.CRAFTED_ITEM.subscribe(new MostSomethingDoneEventRecipient(EAchievement.SO_CRAFTY, playerCharacter -> playerCharacter.getStats().getCraftsCount()));
    EEvent.ENCHANTED_ITEM.subscribe(new MostSomethingDoneEventRecipient(EAchievement.SPELLS_GREAT, playerCharacter -> playerCharacter.getStats().getEnchantmentsCount()));
    EEvent.CLAIMED_ITEM.subscribe(new MostSomethingDoneEventRecipient(EAchievement.MINE_MINE, playerCharacter -> playerCharacter.getStats().getItemsClaimedCount()));

    EEvent.LED_PARTY.subscribe(new MostSomethingDoneEventRecipient(EAchievement.CAPTAIN_MY_CAPTAIN, playerCharacter ->
    {
      if (playerCharacter.getParty().isPrivateParty())
      {
        return 0.0;
      }

      return playerCharacter.getStats().getLeadershipMovesCount();
    }));
  }

  public void subscribe(EventRecipient<? extends Event> recipient)
  {
    getSubscribers(this).add(recipient);
  }

  // Ease-of-use standard case where it just involves a player character.
  public void trigger(PlayerCharacter playerCharacter)
  {
    trigger(new Event(playerCharacter));
  }

  // super ease-of-use to avoid having to test everywhere in code
  public void trigger(Fighter target)
  {
    if (target instanceof PlayerCharacter)
    {
      PlayerCharacter playerCharacter = (PlayerCharacter) target;
      trigger(playerCharacter);
    }
  }

  // More general use-case
  public void trigger(Event event)
  {
    for (EventRecipient<? extends Event> subscriber : getSubscribers(this))
    {
      try
      {
        subscriber.preTrigger(event);
      }
      catch (Exception ex)
      {
        // don't want one subscriber to break events
        Logger.logError(ex);
      }
    }
  }

  public static List<EventRecipient<? extends Event>> getSubscribers(EEvent eEvent)
  {
    if (subscribers == null)
    {
      createSubscribers();
    }

    if (subscribers.get(eEvent) == null)
    {
      buildEvent(eEvent);
    }

    return subscribers.get(eEvent);
  }

  private synchronized static void buildEvent(EEvent eEvent)
  {
    subscribers.computeIfAbsent(eEvent, k -> new ArrayList<>());
  }

  private static synchronized void createSubscribers()
  {
    if (subscribers == null)
    {
      subscribers = new HashMap<>();
    }
  }
}
