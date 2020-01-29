package com.github.dagwud.woodlands.game.commands;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

import java.io.*;

public class PersistObjectCmd extends AbstractCmd
{
  private final String name;
  private final Object object;

  PersistObjectCmd(String name, Object object)
  {
    this.name = name;
    this.object = object;
  }

  @Override
  public void execute() throws Exception
  {
    System.out.println("Persisting object " + name + "...");
    File tmp = writeObject(object);
    System.out.format("Uploading %s to S3 bucket %s...\n", tmp, Settings.S3_BUCKET_NAME);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
    try
    {
      s3.putObject(Settings.S3_BUCKET_NAME, name, tmp);
      System.out.println("Object persisted");
    }
    catch (AmazonServiceException e)
    {
      System.err.println(e.getErrorMessage());
      throw new WoodlandsRuntimeException("Failed to persist", e);
    }
    finally
    {
      s3.shutdown();
    }
  }

  private File writeObject(Object object) throws IOException
  {
    File file = File.createTempFile("s3", "ser");
    try (FileOutputStream fos = new FileOutputStream(file))
    {
      try (ObjectOutputStream oos = new ObjectOutputStream(fos))
      {
        oos.writeObject(object);
      }
    }
    return file;
  }
}
