package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

abstract class RestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;

  RestCmd(int chatId, PlayerCharacter character)
  {
    super(new AbleToActPrerequisite(character));
    this.chatId = chatId;
    this.character = character;
  }

  PlayerCharacter getCharacter()
  {
    return character;
  }

  int getChatId()
  {
    return chatId;
  }

  abstract void scheduleRest(PlayerCharacter restFor);

  final boolean isFullyRested(PlayerCharacter character)
  {
    return character.getStats().getHitPoints() >= character.getStats().getMaxHitPoints().total()
            && character.getStats().getMana() >= character.getStats().getMaxMana().total();
  }

  final boolean isRestedEnough(PlayerCharacter character)
  {
    return (double)(character.getStats().getHitPoints()) /
        (double)(character.getStats().getMaxHitPoints().total()) >= 0.2;
  }
}
