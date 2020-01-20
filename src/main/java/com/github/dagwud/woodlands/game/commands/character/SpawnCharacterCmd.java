package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
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

  public SpawnCharacterCmd(int chatId, String characterName, ECharacterClass characterClass)
  {
    this.chatId = chatId;
    this.characterName = characterName;
    this.characterClass = characterClass;
  }

  @Override
  public void execute()
  {
    GameState gameState = GameStatesRegistry.lookup(chatId);

    CreateCharacterCmd create = new CreateCharacterCmd(characterName, characterClass);
    CommandDelegate.execute(create);
    GameCharacter character = create.getCreatedCharacter();

    SwitchCharacterCmd makeActive = new SwitchCharacterCmd(gameState.getPlayer(), character);
    CommandDelegate.execute(makeActive);

    SendMessageCmd welcomeCmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "Welcome, " + character.getName() + " the " + character.getCharacterClass() + "!");
    CommandDelegate.execute(welcomeCmd);

    MoveToLocationCmd move = new MoveToLocationCmd(gameState, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(move);
  }
}
