package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.character.CreateShadowPlayerCmd;
import com.github.dagwud.woodlands.game.commands.character.SpawnCharacterCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;

public class DoPlayerSetupCmd extends SuspendableCmd
{
  private String characterName;
  private String characterClass;

  DoPlayerSetupCmd(GameState gameState)
  {
    super(gameState, 3);
  }

  @Override
  public void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForCharacterName();
        break;
      case 1:
        receiveCharacterNameAndPromptForClass(capturedInput);
        break;
      case 2:
        receiveClassAndInitStats(capturedInput);
        break;
    }
  }

  private void promptForCharacterName()
  {
    SendMessageCmd cmd = new SendMessageCmd(getGameState().getPlayer().getChatId(), "By what name will you be known?");
    CommandDelegate.execute(cmd);
  }

  private void receiveCharacterNameAndPromptForClass(String capturedInput)
  {
    characterName = capturedInput;

    SendMessageCmd ack = new SendMessageCmd(getGameState().getPlayer().getChatId(), "Very well. So shall you be known.");
    CommandDelegate.execute(ack);

    ChoiceCmd prompt = new ChoiceCmd(getGameState().getPlayer().getChatId(), "Please choose your player class", ECharacterClass.values());
    CommandDelegate.execute(prompt);
  }

  private void receiveClassAndInitStats(String capturedInput)
  {
    characterClass = capturedInput;

    SpawnCharacterCmd cmd = new SpawnCharacterCmd(getGameState().getPlayer().getChatId(), characterName, ECharacterClass.of(characterClass));
    CommandDelegate.execute(cmd);

    CreateShadowPlayerCmd shadow = new CreateShadowPlayerCmd(-100, getGameState().getPlayer().getActiveCharacter());
    CommandDelegate.execute(shadow);
  }
}
