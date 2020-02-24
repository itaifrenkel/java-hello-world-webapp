package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.events.EventRecipient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EEvent
{
  PLAYER_DEATH;

  private static transient Map<EEvent, List<EventRecipient>> subscribers;

  public void subscribe(EventRecipient recipient)
  {
    getSubscribers(this).add(recipient);
  }

  // Making the ease-of-use-assumption now that any event will most likely involve a fighter
  // and any more context can be inferred from the event. This may be completely wrong.
  public void trigger(Fighter fighter)
  {
    for (EventRecipient subscriber : getSubscribers(this))
    {
      subscriber.trigger(fighter);
    }
  }

  public static List<EventRecipient> getSubscribers(EEvent eEvent)
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
