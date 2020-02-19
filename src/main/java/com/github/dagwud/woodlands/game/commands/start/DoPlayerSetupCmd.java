package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
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
  private static final long serialVersionUID = 1L;

  private String characterName;
  private String characterClass;

  DoPlayerSetupCmd(Player player)
  {
    super(player.getPlayerState(), 4);
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
        receiveClass(capturedInput);     
        break;
      case 3:
        initStats();
        break;
    }
  }

  private void receiveClass(String capturedInput)
  {
    characterClass = capturedInput;
  }

  private void promptForCharacterName()
  {
    SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "By what name will you be known?");
    CommandDelegate.execute(cmd);
  }

  private void receiveCharacterNameAndPromptForClass(String capturedInput)
  {
    characterName = capturedInput.trim();
    if (!characterName.matches("[a-zA-Z0-9 _()*?!&\\-']+"))
    {
      SendMessageCmd err = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "That's not an allowed name. Try a more boring name");
      CommandDelegate.execute(err);
      super.rejectCapturedInput();
      return;
    }

    if (characterName.length() > Settings.MAX_CHARACTER_NAME_LENGTH)
    {
      SendMessageCmd err = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "That's too long");
      CommandDelegate.execute(err);
      super.rejectCapturedInput();
      return;
    }

    SendMessageCmd ack = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "Very well. So shall you be known.");
    CommandDelegate.execute(ack);

    ChoiceCmd prompt = new ChoiceCmd(getPlayerState().getPlayer().getChatId(), "Please choose your player class", ECharacterClass.values());
    CommandDelegate.execute(prompt);
  }

  private void initStats()
  {
    ECharacterClass charClass;
    try
    {
      charClass = ECharacterClass.of(characterClass);
    }
    catch (IllegalArgumentException e)
    {
      super.rejectCapturedInput();
      return;
    }

    SpawnCharacterCmd cmd = new SpawnCharacterCmd(getPlayerState().getPlayer().getChatId(), characterName, charClass);
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
        if (c == 'B') shadowClass = ECharacterClass.BRAWLER;
        if (c == 'D') shadowClass = ECharacterClass.DRUID;
        if (c == 'G') shadowClass = ECharacterClass.GENERAL;
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

    if (Settings.AUTO_JOIN_PARTY_NAME != null)
    {
      JoinPartyCmd testing = new JoinPartyCmd(cmd.getSpawned(), Settings.AUTO_JOIN_PARTY_NAME);
      CommandDelegate.execute(testing);
    }
  }
}
