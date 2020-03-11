package com.github.dagwud.woodlands.game.domain;

public enum EAchievement
{
  MUGGED_A_CREATURE("Your Loot Or Your Life", "Through the threat of violence, you've taken a creature's belongings"),
  EVERYONE_FOR_THEMSELVES("Everyone For Themselves", "You were their leader. You made it out, they didn't"),
  ALL_THE_ACHIEVEMENTS("All The Achievements", "You have more achievements than anyone else - that's quite an achievement"),
  DRUNKEN_COMMAND("NOW LISHTEN HERE YOU", "You were drunk enough that you issued a command that was not a command"),
  PARTY_IS_OVER("Party's Over... Forever", "You managed to get your entire party killed. Nice one"),
  CAPTAIN_MY_CAPTAIN("Captain My Captain", "You've led your party the most times"),
  DRUNKEN_VICTORY("Gonna Feel That In The Morning", "Despite your entire party being drunk, you still won a battle"),
  SHUFFLED_OFF_THE_MORTAL_COIL("Shuffled Off The Mortal Coil", "You're definitely not sleeping - you're dead"),
  SO_CRAFTY("So Crafty", "You're the craftiest of the crafty crafters - you've crafted the most items in your party"),
  SPELLS_GREAT("Spells Great", "You spell great, anyone ever tell you that? You have had the most items enchanted"),
  MINE_MINE("Mine Mine Mine Mine", "Everything the light touches is your claimdom, or so it seems - you've claimed the most items"),
  LITTERER("Litterer", "Going green is definitely not a priority to you - you've dropped unwanted items all over the place"),
  PHILANTHROPIST("Philanthropist", "Such a charitable person; you just love giving stuff away"),
  HOARDER("Lazy Hoarder", "I mean, yeah, sure, you're definitely gonna need that. Why not burden the innkeeper with it"),
  FIRST_BLOOD("First Blood", "You've killed your first poor, defenceless, innocent creature that just wanted to live. Nice one"),
  BLOOD_BATH("Blood Bath", "Has anyone ever told you that you're a bit of a monster? You probably would've killed them... like you killed those 100 other creatures"),
  FISTICUFFS("Fisticuffs", "You have bested another player in one-on-one combat"),
  LOST_SPARRING("Tomorrow's Another Day", "You've lost a match in sparring - better luck next time"),
  SCREW_YOU_GUYS("Screw You Guys", "You threw aside feelings and left your party to strike it out alone"),
  I_KNOW_THE_WAY("I Know The Way", "You led a party for the first time - good on you kid");

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

  public boolean heldBy(PlayerCharacter playerCharacter)
  {
    return playerCharacter.getStats().getAchievements().contains(this);
  }

}
