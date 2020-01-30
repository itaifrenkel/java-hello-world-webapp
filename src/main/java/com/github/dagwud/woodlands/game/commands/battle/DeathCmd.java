package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellCmd;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.NonPlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

public class DeathCmd extends AbstractCmd
{
  private final Fighter target;

  public DeathCmd(Fighter target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
    target.getStats().setState(EState.DEAD);
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(target.getSpellAbilities().getPartySpells());
    CommandDelegate.execute(expireAll);

    if (target instanceof NonPlayerCharacter)
    {
      PlayerCharacter owner = ((NonPlayerCharacter)target).getOwnedBy();
      CommandDelegate.execute(new SendMessageCmd(owner.getChatId(), target.getName() + " has died"));
    }
  }
}
