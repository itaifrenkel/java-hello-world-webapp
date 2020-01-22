package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.start.PlayerSetupCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

import java.util.List;

public class SwitchCharacterPromptCmd extends SuspendableCmd
{
  private final Player player;

  public SwitchCharacterPromptCmd(Player player)
  {
    super(player.getPlayerState(), 2);
    this.player = player;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        String[] characters = buildCharactersList(getPlayerState().getPlayer());
        ChoiceCmd choice = new ChoiceCmd(player.getChatId(), "Which character would you like to play as?", characters);
        CommandDelegate.execute(choice);
        break;
      case 1:
        switchTo(capturedInput);
        break;
    }

  }

  private void switchTo(String capturedInput)
  {
    GameCharacter switchTo = findCharacter(capturedInput);
    Player player = getPlayerState().getPlayer();
    if (switchTo == null)
    {
      rejectCapturedInput();
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "That's not a valid option");
      CommandDelegate.execute(cmd);
      return;
    }

    SwitchCharacterCmd switchCmd = new SwitchCharacterCmd(player, switchTo);
    CommandDelegate.execute(switchCmd);

    SendMessageCmd msg = new SendMessageCmd(player.getChatId(), "Now playing as " + switchTo.getName() + " the " + switchTo.getCharacterClass());
    CommandDelegate.execute(msg);
  }

  private GameCharacter findCharacter(String capturedInput)
  {
    for (GameCharacter inactiveCharacter : player.getInactiveCharacters())
    {
      if (inactiveCharacter.summary().equals(capturedInput))
      {
        return inactiveCharacter;
      }
    }
    return null;
  }

  private String[] buildCharactersList(Player player)
  {
    String[] characters = new String[player.getInactiveCharacters().size()];
    List<GameCharacter> inactiveCharacters = player.getInactiveCharacters();
    for (int i = 0; i < inactiveCharacters.size(); i++)
    {
      characters[i] = inactiveCharacters.get(i).summary();
    }
    return characters;
  }
}
