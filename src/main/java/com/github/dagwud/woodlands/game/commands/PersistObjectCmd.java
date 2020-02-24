package com.github.dagwud.woodlands.game.commands;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.log.Logger;

import com.google.gson.*;

import java.io.*;

public class PersistObjectCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final String name;
  private final Object object;
  private final boolean includeJSON;

  PersistObjectCmd(String name, Object object, boolean includeJSON)
  {
    this.name = name;
    this.object = object;
    this.includeJSON = includeJSON;
  }

  @Override
  public void execute() throws Exception
  {
    if (Settings.DEVELOPER_MODE)
    {
      Logger.info("Developer mode active; skipping persistence");
      return;
    }
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Settings.S3_REGION).build();
    try
    {
      Logger.info("Persisting object " + name + "...");
      File tmp = writeObject(object);
      upload(s3, tmp, name);

      if (includeJSON)
      {
        Logger.info("Persisting object json " + name + ".txt...");
        File json = writeObjectJSON(object);
        upload(s3, json, name + ".txt");
      }
    }
    catch (Exception e)
    {
      Logger.info("Failed to persist: " + e);
      throw e;
    }
    finally
    {
      s3.shutdown();
    } 
  }

  private File writeObject(Object object) throws IOException
  {
    File file = File.createTempFile("s3_upload", "ser");
    try (FileOutputStream fos = new FileOutputStream(file))
    {
      try (ObjectOutputStream oos = new ObjectOutputStream(fos))
      {
        oos.writeObject(object);
      }
    }
    return file;
  }

  private File writeObjectJSON(Object object) throws IOException
  {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(object);

    File file = File.createTempFile("s3_upload_json", "txt");
    try (FileWriter fw = new FileWriter(file))
    {
      try (BufferedWriter bw = new BufferedWriter(fw))
      {
        bw.write(json);
      }
    }
    return file;
  }

  private void upload(AmazonS3 s3, File tmp, String name)
  {
    Logger.info("Uploading " + name + " to S3 bucket " + Settings.S3_BUCKET_NAME + "...");
    try
    {
      s3.putObject(Settings.S3_BUCKET_NAME, name, tmp);
      Logger.info("Object persisted");
    }
    catch (AmazonServiceException e)
    {
      Logger.error(e.getErrorMessage());
      throw new WoodlandsRuntimeException("Failed to persist", e);
    }
  }
}
