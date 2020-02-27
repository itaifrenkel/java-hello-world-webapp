package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class AdminDamageCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter character;

  public AdminDamageCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    character.getStats().setHitPoints(1);
  }
}
