package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Party;

import java.util.HashMap;
import java.util.Map;

public class PartyRegistry
{
  private static PartyRegistry instance;
  private final Map<String, Party> parties = new HashMap<>();

  private PartyRegistry()
  {
  }

  public static Party lookup(String partyName)
  {
    PartyRegistry registry = instance();
    if (!registry.parties.containsKey(partyName))
    {
      CreatePartyCmd cmd = new CreatePartyCmd(partyName);
      CommandDelegate.execute(cmd);

      Party party = cmd.getCreatedParty();
      registry.parties.put(partyName, party);
    }
    return registry.parties.get(partyName);
  }

  private static PartyRegistry instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (null != instance)
    {
      return;
    }
    instance = new PartyRegistry();
  }
}
