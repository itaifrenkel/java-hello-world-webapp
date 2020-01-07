package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.gson.game.Weapon;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemsCacheFactoryTest
{
  @Test
  public void testParse() throws UnknownWeaponException
  {
    ItemsCacheFactory f = ItemsCacheFactory.instance();
    Weapon weapon = f.getItems().findWeapon("Dagger");
    assertNotNull(weapon);
    assertEquals(1, weapon.damage.diceCount);
    assertEquals(4, weapon.damage.diceFaces);
    assertFalse(weapon.ranged);
  }
}