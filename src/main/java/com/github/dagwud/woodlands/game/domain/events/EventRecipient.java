package com.github.dagwud.woodlands.game.domain.events;

public interface EventRecipient<T extends Event>
{
  /**
   * Just keeping all the dodgy hacks in one place.
   */
  default void preTrigger(Event event)
  {
    trigger((T) event);
  }

  void trigger(T event);
}
