package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.character.CreateCharacterCmd;
import com.github.dagwud.woodlands.game.commands.character.CreateShadowPlayerCmd;
import com.github.dagwud.woodlands.game.commands.character.SwitchCharacterCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

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

    CreateCharacterCmd create = new CreateCharacterCmd(characterName, ECharacterClass.of(characterClass));
    CommandDelegate.execute(create);
    GameCharacter character = create.getCreatedCharacter();

    SwitchCharacterCmd makeActive = new SwitchCharacterCmd(getGameState().getPlayer(), character);
    CommandDelegate.execute(makeActive);

    SendMessageCmd welcomeCmd = new SendMessageCmd(getGameState().getPlayer().getChatId(), "Welcome, " + character.getName() + " the " + character.getCharacterClass() + "!");
    CommandDelegate.execute(welcomeCmd);

    MoveToLocationCmd move = new MoveToLocationCmd(getGameState(), ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(move);
  }
}
