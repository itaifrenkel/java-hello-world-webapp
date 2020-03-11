package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.character.LeavePartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.*;

import java.util.ArrayList;
import java.util.List;

public class RemovePromptCmd extends SuspendableCmd
{
   private static final long serialVersionUID = 1L;
   private final int chatId;

  public RemovePromptCmd(int chatId, PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2);
    this.chatId = chatId;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }

    switch (phaseToExecute)
    {
      case 0:
        promptForCharacter();
        break;
      case 1:
        remove(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, "Please enter the character name");
    CommandDelegate.execute(cmd);
  }

  private void remove(String name)
  {
    StringBuilder done = new StringBuilder("Vanishing characters...\n");

    List<Fighter> toRemove = new ArrayList<>();
    for (Party party : PartyRegistry.listAllParties())
    {
      for (Fighter character : party.getActiveMembers())
      {
        if (character.getName().equalsIgnoreCase(name))
        {
          toRemove.add(character);
        }
      }
    }


    for (Fighter character : toRemove)
    {
      String partyName = "";
      if (character instanceof GameCharacter)
      {
        GameCharacter gameCharacter = (GameCharacter) character;
        Party party = gameCharacter.getParty();
        LeavePartyCmd leave = new LeavePartyCmd(gameCharacter, party);
        CommandDelegate.execute(leave);
        partyName = party.getName();
      }

      if (character instanceof PlayerCharacter)
      {
        PlayerCharacter playerCharacter = (PlayerCharacter) character;
        Player playedBy = playerCharacter.getPlayedBy();
        playedBy.remove(playerCharacter);
      }
      done.append("Obliterated ").append(character.getName())
              .append(" in party ").append(partyName).append("\n");
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, done.toString());
    CommandDelegate.execute(cmd);
  }
}
