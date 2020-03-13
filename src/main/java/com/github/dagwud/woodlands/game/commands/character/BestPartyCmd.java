package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Get some healthy competition going
 */
public class BestPartyCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private int chatId;

  public BestPartyCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    List<CombinedStats> combinedStatsList = new ArrayList<>();
    Collection<Party> parties = PartyRegistry.listAllParties();
    int partyCount = 0;
    for (Party party : parties)
    {
      if (party.getActiveMembers().size() < 1)
      {
        continue;
      }
      partyCount++;
      CombinedStats partyScore = calculate(party);
      combinedStatsList.add(partyScore);
    }

    if (partyCount == 1)
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "There's just one party, and that's yours. You're in a dictatorship, yes, but you're winning."));
      return;
    }

    combinedStatsList.sort(Comparator.comparing(CombinedStats::getScore));

    StringBuilder stringBuilder = new StringBuilder("The best parties of all time (yeah baby):\n");
    int count = 1;
    for (CombinedStats combinedStats : combinedStatsList)
    {
      stringBuilder.append(count).append(": ").append(combinedStats.getParty().getName()).append(" with ").append(combinedStats.getScore()).append(" points\n");
      count++;
    }

    CommandDelegate.execute(new SendMessageCmd(chatId, stringBuilder.toString()));
  }

  private CombinedStats calculate(Party party)
  {
    double total = 0;
    for (Fighter activeMember : party.getActiveMembers())
    {
      total += activeMember.getStats().getExperience();
    }

    total /= party.getActiveMembers().size();
    return new CombinedStats(party, total);
  }

  private static class CombinedStats
  {
    private Party party;
    private double score;

    public CombinedStats(Party party, double score)
    {
      this.party = party;
      this.score = score;
    }

    public Party getParty()
    {
      return party;
    }

    public double getScore()
    {
      return score;
    }
  }
}
