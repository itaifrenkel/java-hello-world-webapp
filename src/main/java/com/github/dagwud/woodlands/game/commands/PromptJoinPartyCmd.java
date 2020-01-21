package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class PromptJoinPartyCmd extends SuspendableCmd
{
  PromptJoinPartyCmd(GameCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2);
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
        receivePartyNameAndJoin(capturedInput);
        break;
    }
  }

  private void promptForPartyName()
  {
    SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "What party do you wish to join?");
    CommandDelegate.execute(cmd);
  }

  private void receivePartyNameAndJoin(String capturedInput)
  {
    JoinPartyCmd join = new JoinPartyCmd(getPlayerState().getActiveCharacter(), capturedInput);
    CommandDelegate.execute(join);
  }
}
