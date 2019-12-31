package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.InvalidGameDefinition;
import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.adapter.TestJSON;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class MainTest
{
  public static void main(String[] args) throws InvalidGameDefinition
  {
    Gson gson = GsonHelper.createGson();

    byte[] chars = TestJSON.TEST.getBytes();
    InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(chars));

    Root root = gson.fromJson(reader, Root.class);
    System.out.println(root);

    ActionsCache actions = new ActionsCache(root);
    Action action = actions.findAction("ReadPlayerName");
    System.out.println(action);
  }

}
