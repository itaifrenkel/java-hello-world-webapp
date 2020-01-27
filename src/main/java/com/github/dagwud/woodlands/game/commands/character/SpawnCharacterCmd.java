package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class SpawnCharacterCmd extends AbstractCmd
{
  private int chatId;
  private final String characterName;
  private final ECharacterClass characterClass;
  private PlayerCharacter spawned;

  public SpawnCharacterCmd(int chatId, String characterName, ECharacterClass characterClass)
  {
    this.chatId = chatId;
    this.characterName = characterName;
    this.characterClass = characterClass;
  }

  @Override
  public void execute()
  {
    Player player = GameStatesRegistry.lookup(chatId).getPlayer();

    CreateCharacterCmd create = new CreateCharacterCmd(player, characterName, characterClass);
    CommandDelegate.execute(create);
    PlayerCharacter character = create.getCreatedCharacter();

    SwitchCharacterCmd makeActive = new SwitchCharacterCmd(player, character);
    CommandDelegate.execute(makeActive);

    SendMessageCmd welcomeCmd = new SendMessageCmd(player.getChatId(), "Welcome, " + character.getName() + " the " + character.getCharacterClass() + "!");
    CommandDelegate.execute(welcomeCmd);

    MoveToLocationCmd move = new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(move);

    PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, chatId);
    CommandDelegate.execute(periodicSoberUp);

    this.spawned = character;
  }

  public PlayerCharacter getSpawned()
  {
    return spawned;
  }
}
