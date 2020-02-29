package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.creatures.SpawnCreatureFromTemplateCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.*;

public class ListCreaturesCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  public ListCreaturesCmd(int chatId)
  {
    super(chatId);
  }

  @Override
  public void execute()
  {
    List<String> entries = new ArrayList<>();
    List<Creature> creatures = new ArrayList<>(CreaturesCacheFactory.instance().getCache().listAll());
    creatures.sort(Comparator.comparingDouble(o -> o.difficulty));
    for (Creature template : creatures)
    {
      SpawnCreatureFromTemplateCmd spawn = new SpawnCreatureFromTemplateCmd(template);
      CommandDelegate.execute(spawn);
      Creature spawnedCreature = spawn.getSpawnedCreature();

      String desc = "L" + spawnedCreature.difficulty + ": " + spawnedCreature.summary() +
              spawnedCreature.getCarrying().summary(spawnedCreature);

      entries.add(desc);
    }

    for (String entry : entries)
    {
      CommandDelegate.execute(new SendAdminMessageCmd(entry));
    }
  }

}
