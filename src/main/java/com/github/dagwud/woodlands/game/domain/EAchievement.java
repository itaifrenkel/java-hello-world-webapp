package com.github.dagwud.woodlands.game.domain;

public enum EAchievement
{
  MUGGED_A_CREATURE("Your Loot Or Your Life", "Through the threat of violence, you've taken a creature's belongings"),
  EVERYONE_FOR_THEMSELVES("Everyone For Themselves", "You were their leader. You made it out, they didn't");

  private final String achievementName;
  private final String description;

  EAchievement(String achievementName, String description)
  {
    this.achievementName = achievementName;
    this.description = description;
  }

  public String getAchievementName()
  {
    return achievementName;
  }

  public String getDescription()
  {
    return description;
  }

}
