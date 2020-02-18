package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.log.Logger;

public class PersistWorldCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  static final String GAME_STATE_FILE = "GameState.ser";

  private final boolean includeJSON;
  private String filename;

  public PersistWorldCmd()
  {
    this(GAME_STATE_FILE, false);
  }

  public PersistWorldCmd(boolean includeJSON)
  {
    this(GAME_STATE_FILE, includeJSON);
  }

  public PersistWorldCmd(String filename, boolean includeJSON)
  {
    this.filename = filename;
    this.includeJSON = includeJSON;
  }

  @Override
  public void execute()
  {
    GameStatesRegistry gameState = GameStatesRegistry.instance();
    persist(gameState, filename);

    Logger.info("Successfully persisted world!");
  }

  private void persist(Object object, String fileName)
  {
    PersistObjectCmd persist = new PersistObjectCmd(fileName, object, includeJSON);
    CommandDelegate.execute(persist);
  }
}
