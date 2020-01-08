package com.github.dagwud.woodlands.gson.game;

public class Weapon
{
  private static final String MELEE_ICON = "\u2694"; // crossed swords
  private static final String RANGED_ICON = "\ud83c\udff9"; // bow and arrow

  public String name;

  public Damage damage;

  public boolean ranged;

  public String customIcon;

  public boolean preventSpawning;

  public String getIcon()
  {
    if (customIcon != null)
    {
      return customIcon;
    }
    return ranged ? RANGED_ICON : MELEE_ICON;
  }
}
