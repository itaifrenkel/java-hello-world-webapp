package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PersistWorldCmd extends AbstractCmd
{
  public static final String GAME_STATE_FILE = "GameState.ser";
  static final String PARTY_REGISTRY_FILE = "PartyRegistry.ser";

  @Override
  public void execute() throws Exception
  {
    GameStatesRegistry gameState = GameStatesRegistry.instance();
    persist(gameState, GAME_STATE_FILE);
    PartyRegistry partyRegistry = PartyRegistry.instance();
    persist(partyRegistry, PARTY_REGISTRY_FILE);

    System.out.println("Successfully persisted world!");
  }

  private void persist(Object object, String file) throws IOException
  {
    try (FileOutputStream out = new FileOutputStream(file))
    {
      try (ObjectOutputStream os = new ObjectOutputStream(out))
      {
        os.writeObject(object);
      }
    }
  }
}
