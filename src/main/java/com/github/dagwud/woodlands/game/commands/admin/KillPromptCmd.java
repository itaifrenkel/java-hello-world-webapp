package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.DeathCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class KillPromptCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;

  public KillPromptCmd(int chatId, PlayerCharacter character)
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
        kill(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, "Please enter the character name");
    CommandDelegate.execute(cmd);
  }

  private void kill(String name)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }
    StringBuilder done = new StringBuilder("Killing off...\n");
    for (Party party : PartyRegistry.listAllParties())
    {
      for (Fighter character : party.getActiveMembers())
      {
        if (character.getName().equalsIgnoreCase(name))
        {
          DeathCmd cmd = new DeathCmd(character);
          CommandDelegate.execute(cmd);

          done.append("Killed off ").append(character.getName())
                  .append(" in party ").append(party.getName()).append("\n");
        }
      }
    }

    SendMessageCmd cmd = new SendMessageCmd(chatId, done.toString());
    CommandDelegate.execute(cmd);
  }
}
