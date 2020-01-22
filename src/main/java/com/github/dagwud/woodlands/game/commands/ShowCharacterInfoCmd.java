package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class ShowCharacterInfoCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  ShowCharacterInfoCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    CarriedItems carrying = character.getCarrying();

    String message = character.getName() + " " +
            "(L" + stats.getLevel() + " " + character.getCharacterClass() + ")\n" +
            "Location: " + character.getLocation() + "\n" +
            "\n" +
            "❤️: " + stats.getHitPoints() + " / " + stats.getMaxHitPoints() + "\n" +
            "✨: " + stats.getMana() + " / " + stats.getMaxMana() + "\n" +
            "Strength: " + stats.getStrength() + "\n" +
            "Agility: " + stats.getAgility() + "\n" +
            "Constitution: " + stats.getConstitution() + "\n" +
            "Equipped:\n" +
            "• " + describeItem(carrying.getCarriedLeft()) + "\n" +
            "• " + describeItem(carrying.getCarriedRight()) + "\n" +
            "Carrying:\n" +
            describeInactiveItems(carrying) + "\n\n" +
            skilledWith();

    SendMessageCmd cmd = new SendMessageCmd(chatId, message);
    CommandDelegate.execute(cmd);
  }

  private String describeItem(Weapon carrying)
  {
    if (carrying == null)
    {
      return "nothing";
    }
    return carrying.name + " " + carrying.getIcon() + determineDamageText(carrying);
  }

  private String describeInactiveItems(CarriedItems carrying)
  {
    StringBuilder b = new StringBuilder();
    for (Weapon weapon : carrying.getCarriedInactive())
    {
      b.append("• ").append(describeItem(weapon)).append("\n");
    }
    return b.toString();
  }

  private String skilledWith()
  {
    StringBuilder b = new StringBuilder();
    b.append("Skilled with: ");

    boolean first = true;
    for (String weapon : character.getStats().getWeaponBonusDamage().keySet())
    {
      if (!first)
      {
        b.append(", ");
      }
      first = false;
      b.append(weapon);
    }
    return b.toString();
  }

  private String determineDamageText(Weapon carrying)
  {
    int bonusDamage = character.getStats().getWeaponBonusDamage(carrying);

    String damageText = carrying.damage.determineAverageRoll();
    if (bonusDamage != 0)
    {
      damageText += " +" + bonusDamage;
    }
    return damageText;
  }
}
