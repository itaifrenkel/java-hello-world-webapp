package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

@SuppressWarnings("unused") // called at runtime via reflection
public class VerifyOptionAction extends NativeAction
{
  private static final String PARAMETER_NAME_ALLOWED_OPTIONS = "AllowedOptions";
  private static final String PARAMETER_NAME_CHOSEN_OPTIONS = "OptionToVerify";

  @Override
  public InvocationResults invoke(GameState gameState, Variables callDetails) throws IOException
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    String chosen = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_CHOSEN_OPTIONS);
    String allowedOptions = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_ALLOWED_OPTIONS);
    String[] options = allowedOptions.split(",");
    if (!contains(options, chosen))
    {
      TelegramMessageSender.sendMessage(Integer.parseInt(chatId), "That's not a valid option");
      throw new WoodlandsRuntimeException("That's not a valid option");
    }

    return new InvocationResults(new Variables());
  }

  private boolean contains(String[] searchIn, String searchFor)
  {
    for (String option : searchIn)
    {
      if (option.equals(searchFor))
      {
        return true;
      }
    }
    return false;
  }
}
