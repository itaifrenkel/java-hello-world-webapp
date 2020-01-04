package com.github.dagwud.woodlands.gson.telegram;

import com.google.gson.annotations.SerializedName;

public class Message
{
  @SerializedName("message_id")
  public int messageId;

  public User from;

  public String text;
}
