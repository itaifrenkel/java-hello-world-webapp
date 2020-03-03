package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.items.EquippableItem;
import com.google.gson.annotations.SerializedName;

public class Weapon extends EquippableItem
{
  private static final long serialVersionUID = 1L;

  private static final String MELEE_ICON = "\u2694"; // crossed swords
  private static final String RANGED_ICON = "\ud83c\udff9"; // bow and arrow

  public String name;

  public Damage damage;

  public boolean ranged;
  public boolean enchanted;

  @SerializedName(value = "custom_icon")
  public String customIcon;

  public Weapon()
  {
  }

  public Weapon(String name)
  {
    this.name = name;
  }

  public Weapon(Weapon copy)
  {
    this.name = copy.name;
    this.damage = new Damage(copy.damage);
    this.ranged = copy.ranged;
    this.enchanted = copy.enchanted;
    this.customIcon = copy.customIcon;
  }

  @Override
  public String getName()
  {
    return (enchanted ? "Enchanted " : "" ) + name.replaceAll("\\|", "");
  }

  public String[] getSyllables()
  {
    return name.split("\\|");
  }

  @Override
  public String getIcon()
  {
    if (customIcon != null)
    {
      return customIcon;
    }
    return ranged ? RANGED_ICON : MELEE_ICON;
  }

  public String summary(Fighter carrier, boolean includeName)
  {
    return (includeName ? getName() + " " : "") + getIcon() + statsSummary(carrier);
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    String damageText = damage.determineAverageRoll();

    int bonus = carrier.getStats().getBonusDamage() + carrier.getStats().getWeaponBonusDamage(this);
    if (bonus != 0)
    {
      damageText += "+" + bonus;
    }

    return damageText;
  }

}
