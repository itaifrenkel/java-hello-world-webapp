package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class LevelUpCmd extends AbstractCmd
{
  private final int chatId;
  private final PlayerCharacter character;

  public LevelUpCmd(int chatId, PlayerCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    int hitPointsGained = increaseHitPoints();
    character.getStats().setRestPointsMax(character.getStats().getRestPointsMax() + 1);
    character.getStats().setRestPoints(character.getStats().getRestPoints() + 1);

    AbstractCmd msg;
    if (character.getParty().isPrivateParty())
    {
      msg = new SendMessageCmd(chatId, "You have levelled up! Hit Point boost: ❤" + hitPointsGained);
    }
    else
    {
      msg = new SendPartyMessageCmd(character.getParty(), character.getName() + " has levelled up!");
    }
    CommandDelegate.execute(msg);

    character.getStats().setLevel(character.getStats().getLevel() + 1);
  }

  private int increaseHitPoints()
  {
    DiceRollCmd roll = new DiceRollCmd(1, character.getStats().getRestDiceFace());
    CommandDelegate.execute(roll);
    int hpToGain = roll.getTotal() + character.getStats().getConstitutionModifier();
    character.getStats().setMaxHitPoints(character.getStats().getMaxHitPoints() + hpToGain);

    RecoverHitPointsCmd hpCmd = new RecoverHitPointsCmd(character, hpToGain);
    CommandDelegate.execute(hpCmd);
    return hpToGain;
  }
}
