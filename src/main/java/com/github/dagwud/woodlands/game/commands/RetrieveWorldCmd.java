package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

import java.io.*;

public class RetrieveWorldCmd extends AbstractCmd
{
  @Override
  public void execute() throws Exception
  {
    GameStatesRegistry gameState = read(PersistWorldCmd.GAME_STATE_FILE);
    GameStatesRegistry.reload(gameState);

    PartyRegistry partyRegistry = read(PersistWorldCmd.PARTY_REGISTRY_FILE);
    PartyRegistry.reload(partyRegistry);

    System.out.println("Successfully restored world!");

    for (PlayerState player : GameStatesRegistry.allPlayerStates())
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getPlayer().getChatId(), "The air has cleared, and the world seems... different somehow.");
      CommandDelegate.execute(cmd);
    }
  }

  private <T> T read(String file) throws IOException
  {
    try (FileInputStream in = new FileInputStream(file))
    {
      try (ObjectInputStream is = new ObjectInputStream(in))
      {
        try
        {
          return (T) is.readObject();
        }
        catch (ClassNotFoundException e)
        {
          throw new IOException("Error restoring " + file, e);
        }
      }
    }
  }
}
