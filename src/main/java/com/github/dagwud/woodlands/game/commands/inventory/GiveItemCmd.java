package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;

import java.util.ArrayList;
import java.util.List;

public class GiveItemCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final Player player;
  private PlayerCharacter partyMember;

  public GiveItemCmd(Player player)
  {
    super(player.getPlayerState(), 3, new AbleToActPrerequisite(player.getActiveCharacter()));
    this.player = player;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        String[] characters = buildPlayerList(getPlayerState().getPlayer());
        ChoiceCmd choice = new ChoiceCmd(player.getChatId(), "To which noble homey would you like to gift an item?", characters);
        CommandDelegate.execute(choice);
        break;
      case 1:
        getItem(capturedInput);
        break;
      case 2:
        gift(capturedInput);
        break;
    }

  }

  private void gift(String itemName)
  {
    if (itemName.matches("/[Gg][0-9]+"))
    {
      String giveIndex = itemName.substring("/g".length());

      if (giveIndex.equalsIgnoreCase("ogetyourownitem"))
      {
        return;
      }

      try
      {
        int i = Integer.parseInt(giveIndex);
        List<Item> playerInactive = player.getActiveCharacter().getCarrying().getCarriedInactive();
        Item item = playerInactive.get(i);

        DoGiveItemCmd cmd = new DoGiveItemCmd(player.getActiveCharacter(), partyMember, item);
        CommandDelegate.execute(cmd);
        return;
      } catch (NumberFormatException ex)
      {
        // let it fall through
      }
    }

    CommandDelegate.execute(new SendMessageCmd(player.getChatId(), "Look, if you aren't going to take this seriously let's just not do it at all."));
  }

  private void getItem(String capturedInput)
  {
    if (capturedInput.equals("Cancel"))
    {
      resetMenu();
      return;
    }

    GameCharacter gameCharacter = findCharacter(capturedInput);

    if (gameCharacter != null && gameCharacter.getName().equalsIgnoreCase(player.getActiveCharacter().getName()))
    {
      rejectCapturedInput();

      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "No karma for giving to yourself.");
      CommandDelegate.execute(cmd);
      return;
    }

    if (!(gameCharacter instanceof PlayerCharacter))
    {
      rejectCapturedInput();

      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "That player is either not in this room, not in this party, or exists only in your head.");
      CommandDelegate.execute(cmd);
      return;
    }

    if (!gameCharacter.canCarryMore())
    {
      rejectCapturedInput();

      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "That player already has too much stuff - don't be an enabler.");
      CommandDelegate.execute(cmd);
      return;
    }

    partyMember = (PlayerCharacter) gameCharacter;

    List<String> itemsList = getInactiveItemList();

    StringBuilder b = new StringBuilder();
    b.append("Which most excellent item will you give to ").append(partyMember.getName()).append("?\n");
    for (String itemInfo : itemsList)
    {
      b.append(itemInfo).append("\n");
    }
    SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), b.toString());
    CommandDelegate.execute(cmd);
  }

  private void resetMenu()
  {
    ShowMenuCmd showMenuCmd = new ShowMenuCmd(player.getActiveCharacter().getLocation().getMenu(), player.getPlayerState());
    CommandDelegate.execute(showMenuCmd);
  }

  private List<String> getInactiveItemList()
  {
    List<String> itemsList = new ArrayList<>();
    PlayerCharacter character = player.getActiveCharacter();
    List<Item> carriedInactive = character.getCarrying().getCarriedInactive();

    for (int i = 0; i < carriedInactive.size(); i++)
    {
      Item weapon = carriedInactive.get(i);
      itemsList.add("• " + weapon.summary(character) + " (give: /g" + i + ")");
    }
    itemsList.add("• You know what? I'm good (give: /gogetyourownitem)");

    return itemsList;
  }

  private GameCharacter findCharacter(String capturedInput)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();

    for (GameCharacter gameCharacter : activeCharacter.getParty().getActiveMembers())
    {
      if (gameCharacter.getName().equals(capturedInput) && validGiveTarget(activeCharacter, gameCharacter))
      {
        return gameCharacter;
      }
    }
    return null;
  }

  private String[] buildPlayerList(Player player)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();
    List<String> partyMembersInLocation = new ArrayList<>();
    for (GameCharacter gameCharacter : activeCharacter.getParty().getActiveMembers())
    {
      if (validGiveTarget(activeCharacter, gameCharacter))
      {
        partyMembersInLocation.add(gameCharacter.getName());
      }
    }
    partyMembersInLocation.add("Cancel");

    return partyMembersInLocation.toArray(new String[0]);
  }

  private boolean validGiveTarget(PlayerCharacter activeCharacter, GameCharacter gameCharacter)
  {
    return gameCharacter != activeCharacter && !(gameCharacter instanceof NonPlayerCharacter) && gameCharacter.isActive() && gameCharacter.getLocation().equals(activeCharacter.getLocation());
  }
}
