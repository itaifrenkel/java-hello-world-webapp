package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ClearCraftersCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public ClearCraftersCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getChatId());
    this.character = character;
  }

  @Override
  public void execute()
  {
    for (PlayerCharacter activePlayerCharacter : character.getParty().getActivePlayerCharacters()) {
      activePlayerCharacter.getParty().getBlacksmith().completeCrafting(activePlayerCharacter);
      activePlayerCharacter.getParty().getAlchemist().completeCrafting(activePlayerCharacter);
    }
  }
}
