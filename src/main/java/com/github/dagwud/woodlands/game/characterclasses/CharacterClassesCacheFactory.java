package com.github.dagwud.woodlands.game.characterclasses;

import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.game.CharacterClass;
import com.github.dagwud.woodlands.gson.game.CharacterClassesRoot;

import java.io.File;
import java.util.List;

public class CharacterClassesCacheFactory
{
  private static final String CLASSES_FILE = "src/main/resources/classes.json";

  private static CharacterClassesCacheFactory instance;

  private final CharacterClassesCache cache;

  private CharacterClassesCacheFactory()
  {
    CharacterClassesRoot root = GsonHelper.readJSON(new File(CLASSES_FILE), CharacterClassesRoot.class);
    cache = new CharacterClassesCache(root);
  }

  public static CharacterClassesCacheFactory instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private static synchronized void createInstance()
  {
    if (null == instance)
    {
      instance = new CharacterClassesCacheFactory();
    }
  }

  public List<CharacterClass> getCharacterClasses()
  {
    return cache.getCharacterClasses();
  }

}
