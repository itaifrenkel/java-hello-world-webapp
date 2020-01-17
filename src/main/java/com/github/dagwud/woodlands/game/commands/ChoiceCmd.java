package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;

import java.util.ArrayList;
import java.util.List;

class ChoiceCmd extends AbstractCmd
{
  private final int chatId;
  private final String prompt;
  private final Object[] options;

  ChoiceCmd(int chatId, String prompt, Object[] options)
  {
    this.chatId = chatId;
    this.prompt = prompt;
    this.options = options;
  }

  @Override
  public void execute()
  {
    String[] optionsText = new String[options.length];
    for (int i = 0; i < optionsText.length; i++)
    {
      optionsText[i] = options[i].toString();
    }
    String buttons = buildInlineKeyboard(optionsText, 2);
    SendMessageCmd cmd = new SendMessageCmd(chatId, prompt, buttons);
    CommandDelegate.execute(cmd);
  }

  private String buildInlineKeyboard(String[] options, int numCols)
  {
    return " {" +
            "\"resize_keyboard\": true," +
            "\"keyboard\": [" +
            toGrid(options, numCols) +
            "]}";
  }

  private String toGrid(String[] options, int numCols)
  {
    List<List<String>> cols = new ArrayList<>();
    for (int i = 0; i < numCols; i++)
    {
      cols.add(new ArrayList<>());
    }

    for (int i = 0; i < options.length; i++)
    {
      for (int k = 0; k < numCols; k++)
      {
        if (i % numCols == k)
        {
          cols.get(k).add(options[i]);
        }
      }
    }

    StringBuilder grid = new StringBuilder();
    for (int r = 0; r < cols.get(0).size(); r++)
    {
      if (r > 0)
      {
        grid.append(",");
      }
      grid.append("[");
      for (int c = 0; c < cols.size(); c++)
      {
        List<String> col = cols.get(c);
        if (r < col.size())
        {
          if (c > 0)
          {
            grid.append(",");
          }
          grid.append("\"").append(col.get(r)).append("\"");
        }
      }
      grid.append("]");
    }
    return grid.toString();
  }
}
