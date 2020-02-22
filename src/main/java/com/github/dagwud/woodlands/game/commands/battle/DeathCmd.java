package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.NonPlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class DeathCmd extends AbstractCmd
{
   private static final long serialVersionUID = 1L;

   private final Fighter target;

  public DeathCmd(Fighter target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
    target.getStats().setState(EState.DEAD);
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(target.getSpellAbilities().getPassivePartySpells());
    CommandDelegate.execute(expireAll);

    if (target instanceof NonPlayerCharacter)
    {
      Player owner = ((NonPlayerCharacter)target).getOwnedBy();
      CommandDelegate.execute(new SendMessageCmd(owner.getChatId(), target.getName() + " has died"));
    }
    else if (target instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter)target;
      CommandDelegate.execute(new SendPartyAlertCmd(character.getParty(), character.getName() + " has died!"));
    }
  }
}
