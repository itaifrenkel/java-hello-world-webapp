package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class ListAchievementsCmd extends AbstractCmd
{
  private PlayerCharacter playerCharacter;

  public ListAchievementsCmd(PlayerCharacter playerCharacter)
  {
    this.playerCharacter = playerCharacter;
  }

  @Override
  public void execute()
  {
    Set<EAchievement> achievements = playerCharacter.getStats().getAchievements();
    if (achievements.isEmpty())
    {
      CommandDelegate.execute(new SendMessageCmd(playerCharacter, "Uh, you've achieved nothing #awkies"));
      return;
    }

    ArrayList<EAchievement> eAchievements = new ArrayList<>(achievements);
    eAchievements.sort(Comparator.comparing(EAchievement::getAchievementName));

    StringBuilder stringBuilder = new StringBuilder("Your worldly achievements:\n");
    for (EAchievement eAchievement : eAchievements)
    {
      stringBuilder.append(eAchievement.getAchievementName()).append(" - ").append(eAchievement.getDescription()).append("\n");
    }

    CommandDelegate.execute(new SendMessageCmd(playerCharacter, stringBuilder.toString()));
  }
}
