package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public interface EventRecipient
{
  void trigger(PlayerCharacter fighter);
}
