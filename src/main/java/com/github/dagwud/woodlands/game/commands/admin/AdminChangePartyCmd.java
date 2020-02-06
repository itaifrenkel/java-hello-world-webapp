package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AdminChangePartyCmd extends SuspendableCmd
{
  private final int chatId;
  private String partyToJoin;
  private String joiner;

  public AdminChangePartyCmd(int chatId, PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 3);
    this.chatId = chatId;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForCharacterName();
        break;
      case 1:
        acceptCharacterNameAndPromptForParty(capturedInput);
        break;
      case 2:
        acceptPartyName(capturedInput);
        changeParty();
        break;
    }
  }

  private void promptForCharacterName()
  {
    SendMessageCmd msg = new SendMessageCmd(chatId, "Please enter the name of the character to be moved");
    CommandDelegate.execute(msg);
  }

  private void acceptCharacterNameAndPromptForParty(String capturedInput)
  {
    joiner = capturedInput;

    SendMessageCmd msg = new SendMessageCmd(chatId, "Party name?");
    CommandDelegate.execute(msg);
    rejectCapturedInput();
  }

  private void acceptPartyName(String capturedInput)
  {
    partyToJoin = capturedInput;
  }

  private void changeParty()
  {
    if (Settings.ADMIN_CHAT != chatId)
    {
      SendMessageCmd err = new SendMessageCmd(chatId, "You're not an admin. Go away");
      CommandDelegate.execute(err);
      return;
    }

    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      if (playerState != null)
      {
        PlayerCharacter character = playerState.getActiveCharacter();
        if (character != null && character.getName().equalsIgnoreCase(joiner))
        {
          JoinPartyCmd join = new JoinPartyCmd(character, partyToJoin);
          CommandDelegate.execute(join);

          SendMessageCmd ok = new SendMessageCmd(chatId, "Moving " + character.getName());
          CommandDelegate.execute(ok);
        }
      }
    }
  }
}
