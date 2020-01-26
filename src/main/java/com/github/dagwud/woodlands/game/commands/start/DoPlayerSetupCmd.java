package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.CreateShadowPlayerCmd;
import com.github.dagwud.woodlands.game.commands.character.SpawnCharacterCmd;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.Player;

public class DoPlayerSetupCmd extends SuspendableCmd
{
  private String characterName;
  private String characterClass;

  DoPlayerSetupCmd(Player player)
  {
    super(player.getPlayerState(), 3);
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
    if (!characterName.matches("[a-zA-Z0-9 _()*?!&\\-']+"))
    {
      SendMessageCmd err = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "That's not an allowed name. Try a more boring name");
      CommandDelegate.execute(err);
      super.rejectCapturedInput();
      return;
    }

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

    //todo for testing
    if (characterName.startsWith("Dagwud") && !characterName.equals("Dagwud"))
    {
      String shadows = characterName.substring("Dagwud".length());
      characterName = "Dagwud";
      int i = 0;
      for (char c : shadows.toCharArray())
      {
        ECharacterClass shadowClass = ECharacterClass.WIZARD;
        if (c == 'E') shadowClass = ECharacterClass.BRAWLER;
        if (c == 'B') shadowClass = ECharacterClass.DRUID;
        if (c == 'D') shadowClass = ECharacterClass.GENERAL;
        if (c == 'E') shadowClass = ECharacterClass.EXPLORER;
        if (c == 'T') shadowClass = ECharacterClass.TRICKSTER;
        CreateShadowPlayerCmd shadow = new CreateShadowPlayerCmd(-100 - i,
                "Shadow" + (i + 1),
                getPlayerState().getPlayer().getActiveCharacter(), shadowClass);
        RunLaterCmd delayed = new RunLaterCmd(700 * (i + 1), shadow);
        CommandDelegate.execute(delayed);
        i++;
      }
    }

    JoinPartyCmd testing = new JoinPartyCmd(cmd.getSpawned(), "BetaParty");
    CommandDelegate.execute(testing);
  }
}
