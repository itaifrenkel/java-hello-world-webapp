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
    String message = buildInlineKeyboard(options);
    System.out.println("SENT INLINE:");
    System.out.println(message);
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), message);
    return new InvocationResults(new Variables("return", results), ReturnMode.SUSPEND);
  }

  private String buildInlineKeyboard(String[] options)
  {
    StringBuilder b = new StringBuilder();
    b.append("{reply_markup: {")
            .append("inline_keyboard: ["
    );
    for (int i = 0; i < options.length; i++)
    {
      String option = options[i];
      b.append("[{text: '").append(option).append("', '").append(option).append("', ").append(i);
    }
    b.append("]})};");
    return b.toString();
  }
}
