package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.Party;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    return GameStatesRegistry.instance().getPartyRegistry();
  }

  public static Collection<Party> listAllParties()
  {
if (instance().parties.containsKey(null))
{
  instance().parties.put("FIXEDNULL", instance().parties.get(null));
  instance().parties.remove(null);
System.out.println("fixed a null party");
}

    Map<String, Party> parties = instance().parties;
    return Collections.unmodifiableCollection(parties.values());
  }

}
