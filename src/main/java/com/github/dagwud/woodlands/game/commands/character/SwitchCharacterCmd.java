package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class SwitchCharacterCmd extends AbstractCmd
{
  private final Player player;
  private final GameCharacter character;

  public SwitchCharacterCmd(Player player, GameCharacter character)
  {
    this.player = player;
    this.character = character;
  }

  @Override
  public void execute()
  {
    player.setActiveCharacter(character);
  }
}
