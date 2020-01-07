package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.gson.game.ItemsRoot;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.*;

public class ItemsCache
{
  private final Map<String, Weapon> weapons;

  ItemsCache(ItemsRoot root)
  {
    this.weapons = cacheWeapons(root);
  }

  private Map<String, Weapon> cacheWeapons(ItemsRoot root)
  {
    Map<String, Weapon> weapons = new HashMap<>();
    if (root.weapons != null)
    {
      for (Weapon weapon : root.weapons)
      {
        weapons.put(weapon.name, weapon);
      }
    }
    return weapons;
  }

  public Weapon findWeapon(String name) throws UnknownWeaponException
  {
    Weapon found = weapons.get(name);
    if (null == found)
    {
      throw new UnknownWeaponException("No weapon named '" + name + "' exists");
    }
    return found;
  }

  public List<Weapon> getWeapons()
  {
    return new ArrayList<>(weapons.values());
  }
}