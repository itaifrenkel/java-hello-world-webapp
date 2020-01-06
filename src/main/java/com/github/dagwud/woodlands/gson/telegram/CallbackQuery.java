ackage com.github.dagwud.woodlands.gson.telegram;

import com.google.gson.annotations.SerializedName;

public class CallbackQuery
{
  public String id;

  @SerializedName("inline_message_id")
  public int inlineMessageId;

  public User from;

  public Message message;

  public String data;
}
