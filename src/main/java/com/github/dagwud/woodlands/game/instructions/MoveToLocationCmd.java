package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class MoveToLocationCmd extends AbstractCmd
{
  private final int chatId;
  private GameCharacter characterToMove;
  private final ELocation location;

  MoveToLocationCmd(int chatId, GameCharacter characterToMove, ELocation location)
  {
    this.chatId = chatId;
    this.characterToMove = characterToMove;
    this.location = location;
  }

  @Override
  public void execute()
  {
    if (!characterToMove.isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId,"You need to create a character first. Please use /new");
      CommandDelegate.execute(cmd);
      return;
    }

    characterToMove.setLocation(location);
    SendMessageCmd cmd = new SendMessageCmd(chatId, "You are now at " + characterToMove.getLocation());
    CommandDelegate.execute(cmd);

    showMenuForLocation(location);
  }

  private void showMenuForLocation(ELocation location)
  {
    switch (this.location)
    {
      case INN:
        String[] options = {"Leave Items", "Retrieve Items", "Short Rest", "Village Square"};
        ShowMenuCmd cmd = new ShowMenuCmd(chatId, "This is the inn", options);
        CommandDelegate.execute(cmd);
    }
  }
}
