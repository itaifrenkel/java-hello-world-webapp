package com.github.dagwud.woodlands.game.domain.stats;

import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.HashMap;
import java.util.Map;

public class Stats
{
  private int level;
  private int experience;
  private int hitPoints;
  private int maxHitPoints;
  private int mana;
  private int maxMana;
  private Stat strength = new Stat();
  private Stat agility = new Stat();
  private Stat constitution = new Stat();
  private int defenceRating;
  private int restPoints;
  private int restPointsMax;
  private int restDiceFace;

  private int criticalStrikeChanceBonus;

  private Map<String, Integer> weaponBonusHit = new HashMap<>();
  private Map<String, Integer> weaponBonusDamage = new HashMap<>();
  private EState state;

  // thus endeth the serious stats and begin the fun stats
  private int drunkeness;

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

  public Stat getStrength()
  {
    return strength;
  }

  public void setStrength(int strength, int bonus)
  {
    this.strength = new Stat(strength, bonus);
  }

  public Stat getAgility()
  {
    return agility;
  }

  public void setAgility(int agility, int bonus)
  {
    this.agility = new Stat(agility, bonus);
  }

  public Stat getConstitution()
  {
    return constitution;
  }

  public void setConstitution(int constitution, int bonus)
  {
    this.constitution = new Stat(constitution, bonus);
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

  public EState getState()
  {
    return state;
  }

  public void setState(EState state)
  {
    this.state = state;
  }

  public int getDefenceRating()
  {
    return defenceRating;
  }

  public void setDefenceRating(int defenceRating)
  {
    this.defenceRating = defenceRating;
  }

  public int getDrunkeness()
  {
    return drunkeness;
  }

  public void setDrunkeness(int drunkeness)
  {
    this.drunkeness = drunkeness;
  }

  public int getExperience()
  {
    return experience;
  }

  public void setExperience(int experience)
  {
    this.experience = experience;
  }

  public int getCriticalStrikeChanceBonus()
  {
    return criticalStrikeChanceBonus;
  }

  public void setCriticalStrikeChanceBonus(int criticalStrikeChanceBonus)
  {
    this.criticalStrikeChanceBonus = criticalStrikeChanceBonus;
  }
}
