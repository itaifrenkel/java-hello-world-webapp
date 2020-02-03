package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.game.Cache;
import com.github.dagwud.woodlands.gson.game.ItemsRoot;
import com.github.dagwud.woodlands.gson.game.Shield;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemsCache extends Cache
{
  private final Map<String, Weapon> weapons;
  private final Map<String, Shield> shields;

  ItemsCache(ItemsRoot root)
  {
    this.weapons = cache(root.weapons);
    this.shields = cache(root.shields);
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

  public List<Shield> getShields()
  {
    return new ArrayList<>(shields.values());
  }
}