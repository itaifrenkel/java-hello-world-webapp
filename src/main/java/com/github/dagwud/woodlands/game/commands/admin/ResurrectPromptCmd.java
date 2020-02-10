package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.FullHealCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ResurrectPromptCmd extends SuspendableCmd
{
   private static final long serialVersionUID = 1L;
   private final int chatId;

  public ResurrectPromptCmd(int chatId, PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2);
    this.chatId = chatId;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForCharacter();
        break;
      case 1:
        resurrect(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, "Please enter the character name");
    CommandDelegate.execute(cmd);
  }

  private void resurrect(String name)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }

    StringBuilder done = new StringBuilder("Resurrecting...\n");

    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      done.append(resurrect(playerState.getPlayer().getActiveCharacter(), name));
      for (PlayerCharacter inactiveCharacter : playerState.getPlayer().getInactiveCharacters())
      {
        done.append(resurrect(inactiveCharacter, name));
      }
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, done.toString());
    CommandDelegate.execute(cmd);
  }

  private String resurrect(PlayerCharacter character, String name)
  {
    StringBuilder b = new StringBuilder();
    if (character.getName().equalsIgnoreCase(name))
    {
      String msg = resurrect(character.getParty(), character);
      b.append(msg).append("\n");
    }
    return b.toString();
  }

  private String resurrect(Party party, GameCharacter character)
  {
    FullHealCmd cmd = new FullHealCmd(chatId, character);
    CommandDelegate.execute(cmd);

    MoveToLocationCmd move = new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(cmd);

    return "Resurrected " + character.getName() +
            " in party " + party.getName();
  }
}
