package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;
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

    String message = character.getName() + " " + character.getCharacterClass() +
            " - L" + stats.getLevel() + ")\n" +
            "Location: " + character.getLocation() + "\n" +
            "\n" +
            "❤️: " + stats.getHitPoints() + " / " + stats.getMaxHitPoints() + "\n" +
            "✨: " + stats.getMana() + " / " + stats.getMaxMana() + "\n" +
            "Strength: " + stats.getStrength() + "\n" +
            "Agility: " + stats.getAgility() + "\n" +
            "Constitution: " + stats.getConstitution() + "\n" +
            "Carrying:\n" +
            "• " + describeItem(carrying.getCarriedLeft()) + "\n" +
            "• " + describeItem(carrying.getCarriedRight());

    SendMessageCmd cmd = new SendMessageCmd(chatId, message);
    CommandDelegate.execute(cmd);
  }

  private String describeItem(Weapon carrying)
  {
    if (carrying == null)
    {
      return "nothing";
    }
    return carrying.name + " " + carrying.getIcon() + carrying.damage.determineAverageRoll();
  }
}
