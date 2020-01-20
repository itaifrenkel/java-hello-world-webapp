package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RetrieveItemsCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class CreateShadowPlayerCmd extends AbstractCmd
{
  private final GameCharacter shadowOfCharacter;
  private final String shadowName;
  private final int chatId;

  public CreateShadowPlayerCmd(int chatId, String shadowName, GameCharacter shadowOfCharacter)
  {
    this.chatId = chatId;
    this.shadowName = shadowName;
    this.shadowOfCharacter = shadowOfCharacter;
  }

  @Override
  public void execute()
  {
    ECharacterClass shadowClass = ECharacterClass.values()[(int) (Math.random() * ECharacterClass.values().length)];
    SpawnCharacterCmd cmd = new SpawnCharacterCmd(chatId, shadowName, shadowClass);
    CommandDelegate.execute(cmd);
    GameCharacter spawned = cmd.getSpawned();

    RetrieveItemsCmd equipShadowLeft = new RetrieveItemsCmd(spawned);
    CommandDelegate.execute(equipShadowLeft);

    JoinPartyCmd shadowJoin = new JoinPartyCmd(spawned, shadowOfCharacter.getParty().getName());
    CommandDelegate.execute(shadowJoin);

    SendPartyMessageCmd info = new SendPartyMessageCmd(spawned.getParty(), spawned.getName() + " has been given: " + spawned.getCarrying().getCarriedLeft().name + " and " + spawned.getCarrying().getCarriedRight().name);
    CommandDelegate.execute(info);
  }

}
