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
    if (location == ELocation.VILLAGE_SQUARE)
    {
      String[] options = {"The Inn", "The Tavern", "The Mountain", "The Woodlands"};
      ShowMenuCmd cmd = new ShowMenuCmd(chatId, "The bustle of the village square brings a reassuring comfort to weary adventurers - the only danger here is the prices charged by the local bartender.\n\nThe square is dominated on the one side by the local tavern, from which you hear loud music and occasional raised voices.\n\nFacing it, within stumbling range, is the Inn. The innkeeper is a friend, and has offered to look after any items you may wish to leave behind while you're out on your adventures.\n\nTo the north lies a path leading up to The Mountain, and another leading out to the Woodlands.", options);
      CommandDelegate.execute(cmd);
    }
    else if (location == ELocation.INN)
    {
      String[] options = {"Leave Items", "Retrieve Items", "Short Rest", "Village Square"};
      ShowMenuCmd cmd = new ShowMenuCmd(chatId, "This is the inn", options);
      CommandDelegate.execute(cmd);
    }
    else if (location == ELocation.TAVERN)
    {
      String[] options = {"Buy Drinks", "Village Square", "The Mountain"};
      ShowMenuCmd cmd = new ShowMenuCmd(chatId, "This is the Tavern", options);
      CommandDelegate.execute(cmd);
    }
    else if (location == ELocation.MOUNTAIN)
    {
      String[] options = {"The Village"};
      ShowMenuCmd cmd = new ShowMenuCmd(chatId, "This is the Mountain", options);
      CommandDelegate.execute(cmd);
    }
    else if (location == ELocation.WOODLANDS)
    {
      String[] options = {"The Gorge", "The Village"};
      ShowMenuCmd cmd = new ShowMenuCmd(chatId, "This is the Woodlands", options);
      CommandDelegate.execute(cmd);
    }

  }
}
