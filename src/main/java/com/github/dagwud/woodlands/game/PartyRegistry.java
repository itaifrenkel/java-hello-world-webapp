package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Party;

import java.io.Serializable;
import java.util.*;

public class PartyRegistry implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final Map<String, Party> parties = new HashMap<>();

  PartyRegistry()
  {
  }

  public static Party lookup(String partyName)
  {
    PartyRegistry registry = instance();
    String lowerName = partyName.toLowerCase();

    // little bit of trickery to not break the game
    if (!registry.parties.containsKey(partyName) && !registry.parties.containsKey(lowerName))
    {
      CreatePartyCmd cmd = new CreatePartyCmd(partyName);
      CommandDelegate.execute(cmd);

      Party party = cmd.getCreatedParty();
      registry.parties.put(lowerName, party);
    }

    if (!lowerName.equals(partyName) && registry.parties.containsKey(partyName))
    {
      registry.fix(partyName);
    }

    return registry.parties.get(lowerName);
  }

  public static PartyRegistry instance()
  {
    return GameStatesRegistry.instance().getPartyRegistry();
  }

  public static Collection<Party> listAllParties()
  {
    instance().datafix();
    Map<String, Party> parties = instance().parties;
    return Collections.unmodifiableCollection(parties.values());
  }

  private void datafix()
  {
    List<String> fixName = new ArrayList<>();
    for (Map.Entry<String, Party> partyEntry : parties.entrySet())
    {
      Party value = partyEntry.getValue();
      String key = partyEntry.getKey();

      if (value.getName() == null)
      {
        if (key != null)
        {
          value.setName(key);
        }
        SendMessageCmd admin = new SendMessageCmd(Settings.ADMIN_CHAT, "A null-named party was fixed");
        CommandDelegate.execute(admin);
      }
      if (key != null && !key.equals(key.toLowerCase()))
      {
        fixName.add(key);
      }
    }

    for (String name : fixName)
    {
      fix(name);
    }

  }

  private void fix(String name)
  {
    Party party = parties.remove(name);
    parties.put(name.toLowerCase(), party);
  }

}
