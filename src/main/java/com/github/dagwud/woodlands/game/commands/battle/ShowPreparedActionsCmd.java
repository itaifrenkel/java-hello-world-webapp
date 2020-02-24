package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ShowPreparedActionsCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private PlayerCharacter character;

  public ShowPreparedActionsCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    if (character.getParty().getActiveEncounter() == null)
    {
      new SendMessageCmd(character.getPlayedBy().getChatId(), "No battle in progress!").go();
      return;
    }

    String[] spells = character.getSpellAbilities().listPrepared();
    String action = String.join(" and ", spells);
    action = "You will " + (spells.length == 0 ? "" : "cast " + action);
    int attacks = character.getParty().getActiveEncounter().getActionsAllowedPerRound() - character.getSpellAbilities().countPrepared();
    if (attacks > 0)
    {
      if (spells.length != 0)
      {
        action += " and ";
      }
      action += "make " + attacks + " attack" + (attacks == 1 ? "" : "s");
    }
    SendMessageCmd msg = new SendMessageCmd(character.getPlayedBy().getChatId(), action + " on your next turn");
    CommandDelegate.execute(msg);
  }
}
