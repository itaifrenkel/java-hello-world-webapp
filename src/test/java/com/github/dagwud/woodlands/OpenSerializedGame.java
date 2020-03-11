package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameStatesRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class OpenSerializedGame
{
public static final String PATH_TO_FILE = "/Users/evan/Desktop/GameState.ser.gameover.com";

  public static void main(String[] args) throws Exception
  {
    try (FileInputStream fos = new FileInputStream(new File(PATH_TO_FILE)))
    {
      try (ObjectInputStream oos = new ObjectInputStream(fos))
      {
        GameStatesRegistry o = (GameStatesRegistry) oos.readObject();

        List<String> defaultLyrics = GameStatesRegistry.instance().getJukeBox().getLyrics();
        List<String> lyrics1 = o.getJukeBox().getLyrics();
        lyrics1.removeAll(defaultLyrics);


        System.out.println("Added: " + (defaultLyrics.size() - lyrics1.size()));
        for (String lyric : lyrics1)
        {
          System.out.println(lyric.replaceAll("\n", " / "));
        }

      }
    }
  }
}
