package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.character.InitCharacterStatsCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class DoPlayerSetupCmd extends SuspendableCmd
{
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
    getGameState().getPlayer().setActiveCharacter(new GameCharacter());

    SendMessageCmd cmd = new SendMessageCmd(getGameState().getPlayer().getChatId(), "By what name will you be known?");
    CommandDelegate.execute(cmd);
  }

  private void receiveCharacterNameAndPromptForClass(String capturedInput)
  {
    getGameState().getActiveCharacter().setName(capturedInput);

    SendMessageCmd ack = new SendMessageCmd(getGameState().getPlayer().getChatId(), "Very well. So shall you be known.");
    CommandDelegate.execute(ack);

    ChoiceCmd prompt = new ChoiceCmd(getGameState().getPlayer().getChatId(), "Please choose your player class", ECharacterClass.values());
    CommandDelegate.execute(prompt);
  }

  private void receiveClassAndInitStats(String capturedInput)
  {
    GameCharacter character = getGameState().getActiveCharacter();
    character.setCharacterClass(ECharacterClass.of(capturedInput));

    InitCharacterStatsCmd cmd = new InitCharacterStatsCmd(character);
    CommandDelegate.execute(cmd);

    character.setSetupComplete(true);
    SendMessageCmd welcomeCmd = new SendMessageCmd(getGameState().getPlayer().getChatId(), "Welcome, " + character.getName() + " the " + character.getCharacterClass() + "!");
    CommandDelegate.execute(welcomeCmd);

    MoveToLocationCmd move = new MoveToLocationCmd(getGameState(), ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(move);
  }
}
