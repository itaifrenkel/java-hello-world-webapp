package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.ReturnMode;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadOptionAction extends NativeAction
{
  private static final String PARAMETER_NAME_PROMPT = "MessageToPrint";
  private static final String OUTPUT_CHOSEN_OPTION = "ChosenOption";
  private static final String PARAMETER_NAME_OPTIONS = "Options";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails) throws IOException
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    String prompt = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_PROMPT);
    String optionsText = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_OPTIONS);
    String[] options = optionsText.split(",");

    String buttons = buildInlineKeyboard(options, 2);
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), prompt, buttons);

    Variables results = new Variables();
    results.put(OUTPUT_CHOSEN_OPTION, "${buffer}");
    return new InvocationResults(results, ReturnMode.SUSPEND);
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
