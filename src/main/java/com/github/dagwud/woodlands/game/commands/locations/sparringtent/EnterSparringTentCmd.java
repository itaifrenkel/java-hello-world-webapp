package com.github.dagwud.woodlands.game.commands.locations.sparringtent;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;

public class EnterSparringTentCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter activeCharacter;

  public EnterSparringTentCmd(PlayerCharacter activeCharacter)
  {
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    List<PlayerCharacter> partners = activeCharacter.getParty().findPlayerCharactersIn(ELocation.SPARRING_TENT);
    partners.remove(activeCharacter);

    if (partners.isEmpty())
    {
      CommandDelegate.execute(new SendPartyAlertCmd(activeCharacter.getParty(), activeCharacter.getName() + " is waiting for a sparring partner"));
      return;
    }

    if (partners.size() > 1)
    {
      CommandDelegate.execute(new SendMessageCmd(activeCharacter, "There's already a pair sparring in the tent; house rules prevent you from interfering"));
      CommandDelegate.execute(new MoveToLocationCmd(activeCharacter, ELocation.TAVERN));
      return;
    }

    GenerateSparringEncounterCmd cmd = new GenerateSparringEncounterCmd(activeCharacter.getPlayedBy().getPlayerState());
    CommandDelegate.execute(cmd);
  }

}
