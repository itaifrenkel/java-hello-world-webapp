package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class CreateShadowPlayerCmd extends AbstractCmd
{
  private final GameCharacter shadowOfCharacter;
  private final int chatId;
  private GameCharacter spawned;

  public CreateShadowPlayerCmd(int chatId, GameCharacter shadowOfCharacter)
  {
    this.chatId = chatId;
    this.shadowOfCharacter = shadowOfCharacter;
  }

  @Override
  public void execute()
  {
    SpawnCharacterCmd cmd = new SpawnCharacterCmd(chatId, "Shadow " + shadowOfCharacter.getName(), shadowOfCharacter.getCharacterClass());
    CommandDelegate.execute(cmd);
    spawned = cmd.getSpawned();
  }

  public GameCharacter getSpawned()
  {
    return spawned;
  }
}
