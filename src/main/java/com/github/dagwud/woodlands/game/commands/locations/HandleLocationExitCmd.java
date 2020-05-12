package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class HandleLocationExitCmd extends AbstractCmd
{
  private final GameCharacter characterToMove;
  private final ELocation moveTo;
  private final ELocation from;

  public HandleLocationExitCmd(GameCharacter characterToMove, ELocation moveTo, ELocation from)
  {
    this.characterToMove = characterToMove;
    this.moveTo = moveTo;
    this.from = from;
  }

  @Override
  public void execute()
  {
    if (characterToMove instanceof PlayerCharacter)
    {
      PlayerCharacter toMove = (PlayerCharacter) characterToMove;

      if (from != null)
      {
        String exitText = from.getMenu().produceExitText(toMove, moveTo);
        if (exitText != null)
        {
          CommandDelegate.execute(new SendLocationMessageCmd(from, "<i>" + exitText + "</i>", characterToMove));
        }
      }
    }
  }
}
