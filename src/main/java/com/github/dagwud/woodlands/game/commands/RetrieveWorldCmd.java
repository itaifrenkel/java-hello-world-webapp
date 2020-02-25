package com.github.dagwud.woodlands.game.commands;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.admin.PatchWorldCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.log.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveWorldCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final String filename;

  private boolean retrieved;

  public RetrieveWorldCmd()
  {
    this(PersistWorldCmd.GAME_STATE_FILE);
  }

  public RetrieveWorldCmd(String filename)
  {
    this.filename = filename;
  }

  @Override
  public void execute() throws Exception
  {
    List<String> objectNames;
    try
    {
      objectNames = list();
    }
    catch (SdkClientException e)
    {
      Logger.logError(e);
      objectNames = new ArrayList<>();
    }
    if (!objectNames.contains(filename))
    {
      return;
    }

    GameStatesRegistry gameState = read(filename);
    if (null != gameState)
    {
      GameStatesRegistry.reload(gameState);
    }
    else
    {
      GameStatesRegistry.instance();
    }

    if (Scheduler.instance().count() >= 50)
    {
      SendMessageCmd msg = new SendMessageCmd(Settings.ADMIN_CHAT, "<b><i>Aaaaaaah! Too many schedules; resetting</i></b>");
      CommandDelegate.execute(msg);
      Scheduler.instance().clear();
    }

    Scheduler.instance().restoreScheduled();
    ELocation.scheduleRooms();

    Logger.info("Successfully restored world!");

    CommandDelegate.execute(new PatchWorldCmd());

    for (PlayerState player : GameStatesRegistry.allPlayerStates())
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getPlayer().getChatId(), "The air has cleared, and the world seems... different somehow.");
      CommandDelegate.execute(cmd);
    }
  }

  private List<String> list()
  {
    List<String> objectNames = new ArrayList<>(2);

    Logger.info("Checking for persisted world...");
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Settings.S3_REGION).build();
    ListObjectsV2Result result = s3.listObjectsV2(Settings.S3_BUCKET_NAME);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    System.out.format("Objects in S3 bucket %s:\n", Settings.S3_BUCKET_NAME);
    for (S3ObjectSummary os : objects)
    {
      objectNames.add(os.getKey());
      Logger.info("* " + os.getKey());
    }
    return objectNames;
  }

  private <T> T read(String fileName) throws IOException
  {
    File file = download(fileName);
    if (file == null)
    {
      return null;
    }
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
          throw new IOException("Error restoring " + fileName, e);
        }
      }
    }
  }

  private File download(String fileName) throws IOException
  {
    File file = new File(fileName);
    System.out.format("Downloading %s from S3 bucket %s...\n", fileName, Settings.S3_BUCKET_NAME);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Settings.S3_REGION).build();
    try
    {
      S3Object o = s3.getObject(Settings.S3_BUCKET_NAME, fileName);
      try (S3ObjectInputStream s3is = o.getObjectContent())
      {
        try (FileOutputStream fos = new FileOutputStream(file))
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
      return file;
    }
    catch (AmazonServiceException e)
    {
      Logger.error(e.getErrorMessage());
    }
    return null;
  }

  public boolean retrieved()
  {
    return retrieved;
  }
}
