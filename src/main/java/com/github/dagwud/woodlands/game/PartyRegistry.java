package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Party;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PartyRegistry implements Serializable
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

  public static PartyRegistry instance()
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

  public static Collection<Party> listNames()
  {
    return Collections.unmodifiableCollection(instance().parties.values());
  }

  public static void reload(PartyRegistry partyRegistry)
  {
    instance = partyRegistry;
  }

  public static void reset()
  {
    instance = new PartyRegistry();
  }
}
