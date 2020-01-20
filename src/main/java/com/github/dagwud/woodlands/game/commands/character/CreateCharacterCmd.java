package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class CreateCharacterCmd extends AbstractCmd
{
  private final String characterName;
  private final ECharacterClass characterClass;
  private GameCharacter createdCharacter;

  CreateCharacterCmd(String characterName, ECharacterClass characterClass)
  {
    this.characterName = characterName;
    this.characterClass = characterClass;
  }

  @Override
  public void execute()
  {
    GameCharacter character = new GameCharacter();
    character.setName(characterName);
    character.setCharacterClass(characterClass);

    InitCharacterStatsCmd cmd = new InitCharacterStatsCmd(character);
    CommandDelegate.execute(cmd);
    character.setSetupComplete(true);

    createdCharacter = character;
  }

  public GameCharacter getCreatedCharacter()
  {
    return createdCharacter;
  }
}
