package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.LeavePartyCmd;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.math.BigDecimal;

public class WakeCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter activeCharacter;

  public WakeCmd(int chatId, PlayerCharacter activeCharacter)
  {
    super(new AbleToActPrerequisite(activeCharacter));
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    PlayerCharacter dead = null;
    for (PlayerCharacter f : activeCharacter.getParty().getActivePlayerCharacters())
    {
      if (f.isDead())
      {
        dead = f;
      }
    }

    if (dead == null)
    {
      SendMessageCmd c = new SendMessageCmd(chatId, "You don't have anyone to mourn");
      CommandDelegate.execute(c);
      return;
    }

    if (!allAtSameLocation(activeCharacter.getParty()))
    {
      SendMessageCmd c = new SendMessageCmd("It would be disrespectful if not everybody was here to pay their respects");
      CommandDelegate.execute(c);
      return;
    }

    for (int i = 0; i < dead.getStats().getLevel(); i++)
    {
      for (PlayerCharacter c : activeCharacter.getParty().getActivePlayerCharacters())
      {
        SendMessageCmd msg = new SendMessageCmd(c.getPlayedBy().getChatId(), "<b>Raise a glass to those no longer with us</b>");
        CommandDelegate.execute(msg);

        BuyDrinksCmd drink = new BuyDrinksCmd(c.getPlayedBy().getChatId(), c);
        CommandDelegate.execute(drink);
      }
    }
    LeavePartyCmd leave = new LeavePartyCmd(dead, activeCharacter.getParty(), true);
    CommandDelegate.execute(leave);
  }

  private boolean allAtSameLocation(Party party)
  {
    Set<ELocation> locations = new HashSet<>();
    for (GameCharacter member : party.getActiveMembers())
    {
      if (!member.isDead())
      {
        locations.add(member.getLocation());
      }
    }
    return locations.size() == 1;
  }

}
