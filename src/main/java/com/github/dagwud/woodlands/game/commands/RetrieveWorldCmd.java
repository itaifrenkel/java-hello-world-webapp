package com.github.dagwud.woodlands.game.commands;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveWorldCmd extends AbstractCmd
{
  private boolean retrieved;

  @Override
  public void execute() throws Exception
  {
    List<String> objectNames = list();
    if (!objectNames.contains(PersistWorldCmd.GAME_STATE_FILE))
    {
      return;
    }

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

  private List<String> list()
  {
    List<String> objectNames = new ArrayList<>(2);

    System.out.println("Checking for persisted world...");
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Settings.S3_REGION).build();
    ListObjectsV2Result result = s3.listObjectsV2(Settings.S3_BUCKET_NAME);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    System.out.format("Objects in S3 bucket %s:\n", Settings.S3_BUCKET_NAME);
    for (S3ObjectSummary os : objects)
    {
      objectNames.add(os.getKey());
      System.out.println("* " + os.getKey());
    }
    return objectNames;
  }

  private <T> T read(String file) throws IOException
  {
    download(file);
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

  private void download(String fileName) throws IOException
  {
    System.out.format("Downloading %s from S3 bucket %s...\n", fileName, Settings.S3_BUCKET_NAME);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
    try
    {
      S3Object o = s3.getObject(Settings.S3_BUCKET_NAME, fileName);
      try (S3ObjectInputStream s3is = o.getObjectContent())
      {
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
        {
          byte[] read_buf = new byte[1024];
          int read_len;
          while ((read_len = s3is.read(read_buf)) > 0)
          {
            fos.write(read_buf, 0, read_len);
          }
        }
      }
      retrieved = true;
    }
    catch (AmazonServiceException e)
    {
      System.err.println(e.getErrorMessage());
    }
  }

  public boolean retrieved()
  {
    return retrieved;
  }
}
