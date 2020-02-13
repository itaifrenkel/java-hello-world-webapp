package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.character.CheckLevelUpCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class AdminSetXPCmd extends SuspendableCmd
{
  private final int chatId;
  private String targetName;
  private int xp;

  public AdminSetXPCmd(int chatId)
  {
    super(GameStatesRegistry.lookup(chatId), 3);
    this.chatId = chatId;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForCharacterName();
        return;
      case 1:
        targetName = capturedInput;
        promptForXP();
        return;
      case 2:
        captureXP(capturedInput);
        setXP();
    }
  }

  private void promptForCharacterName()
  {
    String[] characters = buildPlayerList(getPlayerState().getPlayer());
    ChoiceCmd choice = new ChoiceCmd(chatId, "Please choose character to have XP set", characters);
    CommandDelegate.execute(choice);
  }

  private String[] buildPlayerList(Player player)
  {
    PlayerCharacter activeCharacter = player.getPlayerState().getActiveCharacter();
    List<String> partyMembers = new ArrayList<>();
    for (GameCharacter gameCharacter : activeCharacter.getParty().getActiveMembers())
    {
      partyMembers.add(gameCharacter.getName());
    }
    partyMembers.add("Cancel");
    return partyMembers.toArray(new String[0]);
  }

  private void captureXP(String capturedInput)
  {
    try
    {
      xp = Integer.parseInt(capturedInput);
    }
    catch (NumberFormatException e)
    {
      SendMessageCmd msg = new SendMessageCmd(chatId, "That's not a number");
      CommandDelegate.execute(msg);
      rejectCapturedInput();
    }
  }

  private void promptForXP()
  {
    SendMessageCmd msg = new SendMessageCmd(chatId, "Please enter XP amount");
    CommandDelegate.execute(msg);
  }

  private void setXP()
  {
    for (GameCharacter member : getPlayerState().getPlayer().getActiveCharacter().getParty().getAllMembers())
    {
      if (member.getName().equalsIgnoreCase(targetName))
      {
        member.getStats().setExperience(xp);
        CommandDelegate.execute(new SendMessageCmd(chatId, "XP of " + member.getName() + " has been set to " + member.getStats().getExperience()));

        if (member instanceof PlayerCharacter)
        {
          CheckLevelUpCmd lvl = new CheckLevelUpCmd((PlayerCharacter) member);
          CommandDelegate.execute(lvl);
        }
      }
    }
  }
}
