package com.github.dagwud.woodlands.game.domain.events;

public interface EventRecipient<T extends Event>
{
  /**
   * Just keeping all the dodgy hacks in one place. We pretrigger first so that
   * we can (unsafe) cast the object to the correct type for when the recipient gets
   * it.
   */
  default void preTrigger(Event event)
  {
    trigger((T) event);
  }

  void trigger(T event);
}
