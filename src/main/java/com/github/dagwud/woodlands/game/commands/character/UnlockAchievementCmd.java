package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.Set;

public class UnlockAchievementCmd extends AbstractCmd
{
  public static final String ACHIEVEMENT = "<i>You've unlocked an achievement: %s.\n%s!</i>";
  private PlayerCharacter playerCharacter;
  private EAchievement achievement;

  public UnlockAchievementCmd(PlayerCharacter playerCharacter, EAchievement achievement)
  {
    this.playerCharacter = playerCharacter;
    this.achievement = achievement;
  }

  @Override
  public void execute()
  {
    Set<EAchievement> achievements = playerCharacter.getStats().getAchievements();

    if (achievements.contains(achievement))
    {
      return;
    }

    achievements.add(achievement);
    String message = String.format(ACHIEVEMENT, achievement.getAchievementName(), achievement.getDescription());
    CommandDelegate.execute(new SendMessageCmd(playerCharacter, message));

    SendPartyMessageCmd sendPartyMessageCmd = new SendPartyMessageCmd(playerCharacter.getParty(), playerCharacter.getName() + " has unlocked achievement " + achievement.getAchievementName());
    CommandDelegate.execute(sendPartyMessageCmd);
  }
}
