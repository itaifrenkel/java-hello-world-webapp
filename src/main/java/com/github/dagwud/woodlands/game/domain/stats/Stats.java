package com.github.dagwud.woodlands.game.domain.stats;

import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.Map;

public class Stats
{
  private int level;
  private int hitPoints;
  private int maxHitPoints;
  private int mana;
  private int maxMana;
  private int strength;
  private int agility;
  private int constitution;
  private Map<String, Integer> weaponBonusHit;
  private Map<String, Integer> weaponBonusDamage;

  public int getLevel()
  {
    return level;
  }

  public void setLevel(int level)
  {
    this.level = level;
  }

  public int getHitPoints()
  {
    return hitPoints;
  }

  public void setHitPoints(int hitPoints)
  {
    this.hitPoints = hitPoints;
  }

  public int getMaxHitPoints()
  {
    return maxHitPoints;
  }

  public void setMaxHitPoints(int maxHitPoints)
  {
    this.maxHitPoints = maxHitPoints;
  }

  public int getMana()
  {
    return mana;
  }

  public void setMana(int mana)
  {
    this.mana = mana;
  }

  public int getMaxMana()
  {
    return maxMana;
  }

  public void setMaxMana(int maxMana)
  {
    this.maxMana = maxMana;
  }

  public int getStrength()
  {
    return strength;
  }

  public void setStrength(int strength)
  {
    this.strength = strength;
  }

  public int getAgility()
  {
    return agility;
  }

  public void setAgility(int agility)
  {
    this.agility = agility;
  }

  public int getConstitution()
  {
    return constitution;
  }

  public void setConstitution(int constitution)
  {
    this.constitution = constitution;
  }

  public int getWeaponBonusHit(Weapon weapon)
  {
    return weaponBonusHit.getOrDefault(weapon.name, 0);
  }

  public void setWeaponBonusHit(Map<String, Integer> weaponBonusHit)
  {
    this.weaponBonusHit = weaponBonusHit;
  }

  public int getWeaponBonusDamage(Weapon weapon)
  {
    return weaponBonusDamage.getOrDefault(weapon.name, 0);
  }

  public void setWeaponBonusDamage(Map<String, Integer> weaponBonusDamage)
  {
    this.weaponBonusDamage = weaponBonusDamage;
  }

  public Map<String, Integer> getWeaponBonusDamage()
  {
    return weaponBonusDamage;
  }
}
