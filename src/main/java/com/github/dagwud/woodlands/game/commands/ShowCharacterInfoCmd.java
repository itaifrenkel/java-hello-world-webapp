package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.start.CharacterIsSetUpPrecondition;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.Map;

public class ShowCharacterInfoCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;

  ShowCharacterInfoCmd(int chatId, PlayerCharacter character)
  {
    super(new CharacterIsSetUpPrecondition(chatId, character));
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
            "Experience: " + character.getStats().getExperience() + "\n" +
            "Location: " + character.getLocation() + "\n" +
            "Short Rests Available: " + character.getStats().getRestPoints() + "/" + character.getStats().getRestPointsMax() + "\n" +
            "\n" +
            "❤: " + stats.getHitPoints() + " / " + stats.getMaxHitPoints() + "\n" +
            "✨: " + stats.getMana() + " / " + stats.getMaxMana() + "\n" +
            "Strength: " + stats.getStrength() + "\n" +
            "Agility: " + stats.getAgility() + "\n" +
            "Constitution: " + stats.getConstitution() + "\n" +
            "\n" +
            buildRecent();

    SendMessageCmd cmd = new SendMessageCmd(chatId, message);
    CommandDelegate.execute(cmd);
  }

  private String describeItem(Item carrying)
  {
    if (carrying == null)
    {
      return "nothing";
    }
    return carrying.summary(character);
  }

  private String describeInactiveItems(CarriedItems carrying)
  {
    StringBuilder b = new StringBuilder();
    for (Item weapon : carrying.getCarriedInactive())
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
    for (Map.Entry<String, Integer> bonus : character.getStats().getWeaponBonusDamage().entrySet())
    {
      if (!first)
      {
        b.append(", ");
      }
      first = false;
      b.append(bonus.getKey()).append(" (+").append(bonus.getValue()).append(")");
    }
    return b.toString();
  }

  private String buildRecent()
  {
    StringBuilder b = new StringBuilder("Recent Victories:").append("\n");
    for (Fighter f : character.getRecentlyDefeated())
    {
      b.append("• ").append(f.getName());
      if (f instanceof Creature)
      {
        b.append(" (L").append(((Creature)f).difficulty).append(")");
      }
      b.append("\n");
    }
    return b.toString();
  }
}
