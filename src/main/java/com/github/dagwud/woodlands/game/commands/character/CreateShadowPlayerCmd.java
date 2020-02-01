package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.EquipItemCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RetrieveItemsCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CreateShadowPlayerCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter shadowOfCharacter;
  private final String shadowName;
  private final int chatId;
  private final ECharacterClass shadowClass;

  public CreateShadowPlayerCmd(int chatId, String shadowName, PlayerCharacter shadowOfCharacter, ECharacterClass shadowClass)
  {
    this.chatId = chatId;
    this.shadowName = shadowName;
    this.shadowOfCharacter = shadowOfCharacter;
    this.shadowClass = shadowClass;
  }

  @Override
  public void execute()
  {
    SpawnCharacterCmd cmd = new SpawnCharacterCmd(chatId, shadowName, shadowClass);
    CommandDelegate.execute(cmd);
    PlayerCharacter spawned = cmd.getSpawned();

    RetrieveItemsCmd equipShadowLeft = new RetrieveItemsCmd(spawned);
    CommandDelegate.execute(equipShadowLeft);

    EquipItemCmd equip = new EquipItemCmd(spawned, chatId, "0");
    CommandDelegate.execute(equip);

    JoinPartyCmd shadowJoin = new JoinPartyCmd(spawned, shadowOfCharacter.getParty().getName());
    CommandDelegate.execute(shadowJoin);

    SendPartyMessageCmd info = new SendPartyMessageCmd(spawned.getParty(),
            spawned.getName() + " (" + spawned.getCharacterClass() + ") has been given: " + spawned.getCarrying().getCarriedLeft().summary(spawned));
    CommandDelegate.execute(info);
  }

}
