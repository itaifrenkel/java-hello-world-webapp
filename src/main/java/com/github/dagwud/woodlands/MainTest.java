package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.InvalidGameDefinition;
import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.adapter.TestJSON;

public class MainTest
{
  public static void main(String[] args) throws InvalidGameDefinition
  {
    Root root = GsonHelper.readJSON(TestJSON.TEST, Root.class);
    System.out.println(root);

    ActionsCache actions = new ActionsCache(root);
    Action action = actions.findAction("ReadPlayerName");
    System.out.println(action);
  }

}
