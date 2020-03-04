package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.ShowCharacterInfoCmd;
import com.github.dagwud.woodlands.game.commands.battle.DeathCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.commands.inventory.InventoryCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;
import java.util.ArrayList;

public class GiftItemCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final Party party;
  private PlayerCharacter recipient;

  public GiftItemCmd(int chatId, PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 3);
    this.chatId = chatId;
    this.party = character.getParty();
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
        acceptCharacterAndPromptForItem(capturedInput);
        break;
      case 2:
        acceptItemAndGiveGift(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    ShowPlayerChoiceCmd cmd = new ShowPlayerChoiceCmd(chatId, "Which player?", party);
    CommandDelegate.execute(cmd);
  }

  private void acceptCharacterAndPromptForItem(String name)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }

    recipient = null;
    for (GameCharacter gameCharacter : party.getAllMembers())
    {
      if (gameCharacter instanceof PlayerCharacter)
      {
        PlayerCharacter character = (PlayerCharacter)gameCharacter;
        if (character.getName().equalsIgnoreCase(name))
        {
          recipient = character;
        }
      }
    }
    if (recipient == null)
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "Nope. Try again"));
      rejectCapturedInput();
      return;
    }

    CommandDelegate.execute(new SendMessageCmd(chatId, "What would you like to gift to " + recipient.getName() + "?"));
  }

  private void acceptItemAndGiveGift(String itemName)
  {
    CommandDelegate.execute(new SendMessageCmd(chatId, "Todo gift to " + recipient.getName() + ": " + itemName));
  }
}
