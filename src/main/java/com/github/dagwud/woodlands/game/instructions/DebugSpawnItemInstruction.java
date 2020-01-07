package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class DebugSpawnItemInstruction extends GameInstruction
{
  private static final Random random = new Random(System.currentTimeMillis());
  private static final String MELEE_ICON = "\u2694"; // crossed swords
  private static final String RANGED_ICON = "\ud83c\udff9"; // bow and arrow

  @Override
  public void execute(GameState gameState) throws IOException
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");

    List<Weapon> allWeapons = ItemsCacheFactory.instance().getItems().getWeapons();
    int rand = random.nextInt(allWeapons.size());
    Weapon chosenWeapon = allWeapons.get(rand);
    String weaponText = buildWeaponText(chosenWeapon);
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), "You got a " + weaponText);
  }

  private String buildWeaponText(Weapon chosenWeapon)
  {
    StringBuilder b = new StringBuilder();
    b.append(chosenWeapon.name);
    b.append(" ");
    b.append(chosenWeapon.ranged ? RANGED_ICON : MELEE_ICON );
    b.append(chosenWeapon.damage.determineAverageRoll());
    return b.toString();
  }
}
