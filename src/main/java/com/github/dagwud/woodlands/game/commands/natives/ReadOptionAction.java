package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.ReturnMode;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadOptionAction extends NativeAction
{
  private static final String PARAMETER_NAME_PROMPT = "MessageToPrint";
  private static final String OUTPUT_CHOSEN_OPTION = "ChosenOption";
  private static final String PARAMETER_NAME_OPTIONS = "OptionsCSV";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails) throws IOException
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    String prompt = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_PROMPT);
    String optionsText = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_OPTIONS);
    String[] options = optionsText.split(",");

    String buttons = buildInlineKeyboard(options);
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), prompt, buttons);

    Variables results = new Variables();
    results.put(OUTPUT_CHOSEN_OPTION, "${buffer}");
    return new InvocationResults(results, ReturnMode.SUSPEND);
  }

  private String buildInlineKeyboard(String[] options)
  {
    StringBuilder b = new StringBuilder();
    b.append(" {")
            .append("\"inline_keyboard\": ["
    );
    for (int i = 0; i < options.length; i++)
    {
      String option = options[i];
      if (i > 0)
      {
        b.append(",");
      }
      b.append("[{\"text\": \"").append(option).append("\", ")
              .append("\"callback_data\": \"").append(option).append("\"}]");
    }
    b.append("]}");
    
    return b.toString();
  }
}
