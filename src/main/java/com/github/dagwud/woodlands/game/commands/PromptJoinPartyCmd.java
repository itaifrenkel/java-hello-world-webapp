package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PromptJoinPartyCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;
  private static final long NO_GROUP_CHAT = -1L;
  private static final long NO_CHANGE_GROUP_CHAT = 0L;
  private String partyToJoin;
  private Long alertChannelChatId;

  PromptJoinPartyCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 3);
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForPartyName();
        break;
      case 1:
        receivePartyNameAndPromptAlertGroup(capturedInput);
        break;
      case 2:
        receiveAlertGroupChat(capturedInput);
        join();
    }
  }

  private void promptForPartyName()
  {
    SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "What party do you wish to join?");
    CommandDelegate.execute(cmd);
  }

  private void receivePartyNameAndPromptAlertGroup(String capturedInput)
  {
    partyToJoin = capturedInput;
    SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(),
            "To which group should alerts be published? Enter the Telegram group chat ID, or " + NO_GROUP_CHAT + " to turn off " +
                    "group alerts for this party. Enter " + NO_CHANGE_GROUP_CHAT + " to leave the group setting as it is (e.g. if you're new to an existing party).\n" +
                    "You can try inviting @RawDataBot to your group chat to find the group's ID\n" +
                    "Remember to invite the Woodlands bot to your group chat!");
    CommandDelegate.execute(cmd);
  }

  private void receiveAlertGroupChat(String capturedInput)
  {
    Long groupChatId = null;
    try
    {
      long chatId = Long.parseLong(capturedInput);
      if (chatId <= -10000L || chatId == NO_CHANGE_GROUP_CHAT || chatId == NO_GROUP_CHAT)
      {
        groupChatId = chatId;
      }
    }
    catch (NumberFormatException ignored)
    {
      // fall through
    }
    if (null != groupChatId)
    {
      alertChannelChatId = groupChatId;
      return;
    }
    SendMessageCmd err = new SendMessageCmd(getPlayerState().getPlayer().getChatId(),
            "That's not a valid group chat ID. A group chat should have a negative number corresponding " +
                    "to the Telegram group chat. Try inviting @RawDataBot to get your group chat ID");
    CommandDelegate.execute(err);
    rejectCapturedInput();
  }

  private void join()
  {
    JoinPartyCmd join = new JoinPartyCmd(getPlayerState().getActiveCharacter(), partyToJoin);
    CommandDelegate.execute(join);

    if (alertChannelChatId != NO_CHANGE_GROUP_CHAT)
    {
      if (alertChannelChatId == NO_GROUP_CHAT)
      {
        new SendPartyAlertCmd(getPlayerState().getActiveCharacter().getParty(), "Group alerts will no longer be posted here").go();
        getPlayerState().getActiveCharacter().getParty().setAlertChatId(null);
      }
      else
      {
        boolean changed = false;
        Long prevChatId = getPlayerState().getActiveCharacter().getParty().getAlertChatId();
        if (null != prevChatId && !prevChatId.equals(alertChannelChatId))
        {
          changed = true;
        }
        if (changed)
        {
          new SendPartyAlertCmd(getPlayerState().getActiveCharacter().getParty(), "Group alerts will no longer be posted here").go();
        }
        getPlayerState().getActiveCharacter().getParty().setAlertChatId(alertChannelChatId);
        if (changed)
        {
          new SendPartyAlertCmd(getPlayerState().getActiveCharacter().getParty(), "Group alerts for " + getPlayerState().getActiveCharacter().getParty().getName() + " will be posted here").go();
        }
      }
    }
  }
}
