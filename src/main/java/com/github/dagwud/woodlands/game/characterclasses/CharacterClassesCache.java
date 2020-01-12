package com.github.dagwud.woodlands.game.characterclasses;

import com.github.dagwud.woodlands.gson.game.CharacterClass;
import com.github.dagwud.woodlands.gson.game.CharacterClassesRoot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterClassesCache
{
  private final Map<String, CharacterClass> characterClasses;

  CharacterClassesCache(CharacterClassesRoot root)
  {
    this.characterClasses = cacheCharacterClasses(root);
  }

  private Map<String, CharacterClass> cacheCharacterClasses(CharacterClassesRoot root)
  {
    Map<String, CharacterClass> characterClasses = new HashMap<>();
    if (root.characterClasses != null)
    {
      for (CharacterClass characterClass : root.characterClasses)
      {
        characterClasses.put(characterClass.name, characterClass);
      }
    }
    return characterClasses;
  }

  public CharacterClass findWeapon(String name) throws UnknownCharacterClassException
  {
    CharacterClass found = characterClasses.get(name);
    if (null == found)
    {
      throw new UnknownCharacterClassException("No character class named '" + name + "' exists");
    }
    return found;
  }

  public List<CharacterClass> getCharacterClasses()
  {
    return new ArrayList<>(characterClasses.values());
  }
}