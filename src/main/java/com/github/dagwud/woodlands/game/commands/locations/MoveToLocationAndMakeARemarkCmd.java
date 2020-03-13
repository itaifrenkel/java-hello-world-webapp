package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class MoveToLocationAndMakeARemarkCmd extends MoveToLocationCmd
{
  private static final long serialVersionUID = 1L;

  private String remark;

  public MoveToLocationAndMakeARemarkCmd(GameCharacter characterToMove, ELocation location, String remark)
  {
    super(characterToMove, location);
    this.remark = remark;
  }

  @Override
  public void execute()
  {
    super.execute();
    if (characterToMove instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendMessageCmd((PlayerCharacter)characterToMove, remark));
    }
  }
}
