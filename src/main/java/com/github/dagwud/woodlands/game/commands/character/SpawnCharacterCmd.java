package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class SpawnCharacterCmd extends AbstractCmd
{
  private int chatId;
  private final String characterName;
  private final ECharacterClass characterClass;
  private GameCharacter spawned;

  public SpawnCharacterCmd(int chatId, String characterName, ECharacterClass characterClass)
  {
    this.chatId = chatId;
    this.characterName = characterName;
    this.characterClass = characterClass;
  }

  @Override
  public void execute()
  {
    PlayerState playerState = GameStatesRegistry.lookup(chatId);

    CreateCharacterCmd create = new CreateCharacterCmd(playerState.getPlayer(), characterName, characterClass);
    CommandDelegate.execute(create);
    GameCharacter character = create.getCreatedCharacter();

    SwitchCharacterCmd makeActive = new SwitchCharacterCmd(playerState.getPlayer(), character);
    CommandDelegate.execute(makeActive);

    SendMessageCmd welcomeCmd = new SendMessageCmd(playerState.getPlayer().getChatId(), "Welcome, " + character.getName() + " the " + character.getCharacterClass() + "!");
    CommandDelegate.execute(welcomeCmd);

    MoveToLocationCmd move = new MoveToLocationCmd(playerState, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(move);

    this.spawned = character;
  }

  public GameCharacter getSpawned()
  {
    return spawned;
  }
}
