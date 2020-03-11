package com.github.dagwud.woodlands.game.domain.events.achievements;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerQuantifier;
import com.github.dagwud.woodlands.game.domain.events.Event;
import com.github.dagwud.woodlands.game.domain.events.EventRecipient;

public class MostSomethingDoneEventRecipient implements EventRecipient<Event>
{
  private final EAchievement achievement;
  private final PlayerQuantifier playerQuantifier;

  public MostSomethingDoneEventRecipient(EAchievement achievement, PlayerQuantifier playerQuantifier)
  {
    this.achievement = achievement;
    this.playerQuantifier = playerQuantifier;
  }

  @Override
  public void trigger(Event event)
  {
    PlayerCharacter playerCharacter = event.getPlayerCharacter();

    double totalItems = playerQuantifier.quantify(playerCharacter);

    if (totalItems == 0.0)
    {
      return;
    }

    PlayerCharacter currentWinner = null;
    for (PlayerCharacter activePlayerCharacter : playerCharacter.getParty().getActivePlayerCharacters())
    {
      if (activePlayerCharacter == playerCharacter)
      {
        continue;
      }

      if (playerQuantifier.quantify(activePlayerCharacter) >= totalItems)
      {
        return;
      }

      if (achievement.heldBy(activePlayerCharacter))
      {
        currentWinner = activePlayerCharacter;
      }
    }

    if (currentWinner != null)
    {
      CommandDelegate.execute(new SendMessageCmd(currentWinner, "You've lost the " + achievement.getAchievementName() + "achievement to " + playerCharacter.getName()));
      currentWinner.getStats().getAchievements().remove(achievement);
    }

    CommandDelegate.execute(new UnlockAchievementCmd(playerCharacter, achievement));
  }
}
