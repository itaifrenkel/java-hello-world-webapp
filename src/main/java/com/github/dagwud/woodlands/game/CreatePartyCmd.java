package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Party;

public class CreatePartyCmd extends AbstractCmd
{
  private final String partyName;
  private Party createdParty;

  CreatePartyCmd(String partyName)
  {
    this.partyName = partyName;
  }

  @Override
  public void execute()
  {
    Party party = new Party();
    party.setName(partyName);
    createdParty = party;
  }

  public Party getCreatedParty()
  {
    return createdParty;
  }
}
