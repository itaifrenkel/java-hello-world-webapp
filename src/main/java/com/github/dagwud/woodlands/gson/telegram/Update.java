package com.github.dagwud.woodlands.gson.telegram;

import com.google.gson.annotations.SerializedName;

public class Update
{
  @SerializedName("update_id")
  public int updateId;

  @SerializedName("message")
  public Message message;

  @SerializedName("callback_query")
  public CallbackQuery callbackQuery;
}
