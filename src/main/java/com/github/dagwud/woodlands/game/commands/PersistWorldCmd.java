package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class PersistWorldCmd extends AbstractCmd
{
  static final String GAME_STATE_FILE = "GameState.ser";
  static final String PARTY_REGISTRY_FILE = "PartyRegistry.ser";

  @Override
  public void execute()
  {
    GameStatesRegistry gameState = GameStatesRegistry.instance();
    persist(gameState, GAME_STATE_FILE);
    PartyRegistry partyRegistry = PartyRegistry.instance();
    persist(partyRegistry, PARTY_REGISTRY_FILE);

    System.out.println("Successfully persisted world!");
  }

  private void persist(Object object, String fileName)
  {
    PersistObjectCmd persist = new PersistObjectCmd(fileName, object);
    CommandDelegate.execute(persist);
  }
}
