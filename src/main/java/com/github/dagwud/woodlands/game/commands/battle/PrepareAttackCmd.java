package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.prerequisite.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PrepareAttackCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private PlayerCharacter character;

  public PrepareAttackCmd(PlayerCharacter character)
  {
    super(new AbleToActPrerequisite(character));
    this.character = character;
  }

  @Override
  public void execute()
  {
    Encounter activeEncounter = character.getParty().getActiveEncounter();
    if (activeEncounter == null)
    {
      return;
    }

    if (activeEncounter.hasFightingStarted())
    {
      CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "Too late - the round has already started"));
    }
    // Don't actually prepare a spell; just make a space for one. Any unused spell slots will be filled with attacks:
    if (character.getSpellAbilities().hasPreparedSpell())
    {
      character.getSpellAbilities().popPrepared();
    }

    activeEncounter.setHasAnyPlayerActivityPrepared(true);

    ShowPreparedActionsCmd msg = new ShowPreparedActionsCmd(character);
    CommandDelegate.execute(msg);
  }

}
