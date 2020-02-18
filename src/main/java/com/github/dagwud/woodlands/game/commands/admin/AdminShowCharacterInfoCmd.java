package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.ShowCharacterInfoCmd;
import com.github.dagwud.woodlands.game.commands.battle.DeathCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.InventoryCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AdminShowCharacterInfoCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;

  public AdminShowCharacterInfoCmd(int chatId, PlayerCharacter character)
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
        show(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, "Please enter the character name");
    CommandDelegate.execute(cmd);
  }

  private void show(String name)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }

    for (Party party : PartyRegistry.listAllParties())
    {
      for (GameCharacter gameCharacter : party.getActiveMembers())
      {
        if (gameCharacter instanceof PlayerCharacter)
        {
          PlayerCharacter character = (PlayerCharacter)gameCharacter;
          if (character.getName().equalsIgnoreCase(name))
          {
            ShowCharacterInfoCmd cmd = new ShowCharacterInfoCmd(chatId, character);
            CommandDelegate.execute(cmd);

            InventoryCmd inv = new InventoryCmd(chatId, character);
            CommandDelegate.execute(inv);
          }
        }
      }
    }
  }
}
