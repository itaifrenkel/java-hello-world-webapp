package com.github.dagwud.woodlands.game.domain;

public enum EAchievement
{
  MUGGED_A_CREATURE("Your Loot Or Your Life", "Through the threat of violence, you've taken a creature's belongings"),
  EVERYONE_FOR_THEMSELVES("Everyone For Themselves", "You were their leader. You made it out, they didn't"),
  ALL_THE_ACHIEVEMENTS("All The Achievements", "You have more achievements than anyone else - that's quite an achievement"),
  DRUNKEN_COMMAND("NOW LISHTEN HERE YOU", "You were drunk enough that you issued a command that was not a command"),
  PARTY_IS_OVER("Party's Over... Forever", "You managed to get your entire party killed. Nice one"),
  DRUNKEN_VICTORY("Gonna Feel That In The Morning", "Despite your entire party being drunk, you still won a battle"),
  SHUFFLED_OFF_THE_MORTAL_COIL("Shuffled Off The Mortal Coil", "You're definitely not sleeping - you're dead");

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
