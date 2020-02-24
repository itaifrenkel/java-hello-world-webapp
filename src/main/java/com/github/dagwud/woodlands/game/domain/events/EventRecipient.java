package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.Fighter;

public interface EventRecipient
{
  void trigger(Fighter fighter);
}
