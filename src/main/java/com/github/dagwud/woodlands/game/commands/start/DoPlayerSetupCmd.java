package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.character.CreateShadowPlayerCmd;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.character.SpawnCharacterCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;

public class DoPlayerSetupCmd extends SuspendableCmd
{
  private String characterName;
  private String characterClass;

  DoPlayerSetupCmd(PlayerState playerState)
  {
    super(playerState, 3);
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
    SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "By what name will you be known?");
    CommandDelegate.execute(cmd);
  }

  private void receiveCharacterNameAndPromptForClass(String capturedInput)
  {
    characterName = capturedInput;

    SendMessageCmd ack = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "Very well. So shall you be known.");
    CommandDelegate.execute(ack);

    ChoiceCmd prompt = new ChoiceCmd(getPlayerState().getPlayer().getChatId(), "Please choose your player class", ECharacterClass.values());
    CommandDelegate.execute(prompt);
  }

  private void receiveClassAndInitStats(String capturedInput)
  {
    characterClass = capturedInput;

    SpawnCharacterCmd cmd = new SpawnCharacterCmd(getPlayerState().getPlayer().getChatId(), characterName, ECharacterClass.of(characterClass));
    CommandDelegate.execute(cmd);

    JoinPartyCmd join = new JoinPartyCmd(cmd.getSpawned(), "TestParty");
    CommandDelegate.execute(join);

    CreateShadowPlayerCmd shadow = new CreateShadowPlayerCmd(-100, getPlayerState().getPlayer().getActiveCharacter());
    CommandDelegate.execute(shadow);

    JoinPartyCmd shadowJoin = new JoinPartyCmd(shadow.getSpawned(), "TestParty");
    CommandDelegate.execute(shadowJoin);
  }
}
