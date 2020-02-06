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
    instance().datafix();
    Map<String, Party> parties = instance().parties;
    return Collections.unmodifiableCollection(parties.values());
  }

  private void datafix()
  {
    for (Party value : parties.values())
    {
      if (value.getName() == null)
      {
        SendMessageCmd admin = new SendMessageCmd(Settings.ADMIN_CHAT, "A null-named party was fixed");
        CommandDelegate.execute(admin);
      }
    }
  }

}
