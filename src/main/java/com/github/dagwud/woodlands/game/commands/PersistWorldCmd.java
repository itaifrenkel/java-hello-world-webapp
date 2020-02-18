package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.log.Logger;

public class PersistWorldCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  static final String GAME_STATE_FILE = "GameState_developer.ser";

  private final boolean includeJSON;

  public PersistWorldCmd()
  {
    this(false);
  }

  public PersistWorldCmd(boolean includeJSON)
  {
    this.includeJSON = includeJSON;
  }

  @Override
  public void execute()
  {
    GameStatesRegistry gameState = GameStatesRegistry.instance();
    persist(gameState, GAME_STATE_FILE);

    Logger.info("Successfully persisted world!");
  }

  private void persist(Object object, String fileName)
  {
    PersistObjectCmd persist = new PersistObjectCmd(fileName, object, includeJSON);
    CommandDelegate.execute(persist);
  }
}
