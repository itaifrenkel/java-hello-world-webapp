package com.github.dagwud.woodlands.game.commands.admin;

import com.amazonaws.services.dynamodbv2.xspec.S;
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

    int batchSize = 10;
    int count = 0;
    StringBuilder b = new StringBuilder();
    for (String entry : entries)
    {
      b.append(entry);
      count++;
      if (count >= batchSize)
      {
        CommandDelegate.execute(new SendAdminMessageCmd(b.toString()));
        count = 0;
        b = new StringBuilder();
      }
      else
      {
        b.append("\n");
      }
    }
  }

}
