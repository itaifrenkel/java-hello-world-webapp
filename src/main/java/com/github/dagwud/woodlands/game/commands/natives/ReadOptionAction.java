package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadOptionAction extends NativeAction
{
  private static final String OUTPUT_CHOSEN_OPTION = "ChosenOption";
  private static final String PARAMETER_NAME_OPTIONS = "OptionsCSV";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails) throws IOException
  {
    String optionsText = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_OPTIONS);
    String[] options = optionsText.split(",");
    System.out.println("<<< Choose option: " + Arrays.toString(options));
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CHOSEN_OPTION, "${buffer}");
    String chatId = gameState.getVariables().lookupVariableValue("chatId");
    String buttons = buildInlineKeyboard(options);
    System.out.println("SENT INLINE:");
    System.out.println(buttons);
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), "Please make a choice", buttons);
    return new InvocationResults(new Variables(results), ReturnMode.SUSPEND);
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
