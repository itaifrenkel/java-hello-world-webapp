package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

public abstract class ConsumableTrinket extends Trinket
{
  private final String name;
  private final String icon;

  ConsumableTrinket(String name, String icon)
  {
    this.name = name;
    this.icon = icon;
  }

  @Override
  public void doEquip(Fighter character)
  {
    equip(character);
  }

  @Override
  public final void equip(Fighter fighter)
  {
    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) fighter;
      SendMessageCmd msg = new SendMessageCmd(character.getPlayedBy().getChatId(), produceConsumptionMessage());
      CommandDelegate.execute(msg);
    }

    consume(fighter);
  }

  abstract void consume(Fighter fighter);

  abstract String produceConsumptionMessage();

  @Override
  public final String getName()
  {
    return name;
  }

  @Override
  public final String getIcon()
  {
    return icon;
  }

  @Override
  public final void unequip(Fighter fighter)
  {
  }
}
