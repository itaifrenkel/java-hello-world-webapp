package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

@SuppressWarnings("unused") // called at runtime via reflection
public class SpawnItemAction extends NativeAction
{
  private static final String MELEE_ICON = "\u2694"; // crossed swords
  private static final String RANGED_ICON = "\ud83c\udff9"; // bow and arrow

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");
    List<Weapon> allWeapons = ItemsCacheFactory.instance().getItems().getWeapons();
    int rand = (int) (Math.random() * allWeapons.size());
    Weapon chosenWeapon = allWeapons.get(rand);
    String weaponText = buildWeaponText(chosenWeapon);

    Variables result = new Variables();
    result.put("SpawnedItem", weaponText);
    return new InvocationResults(result);
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
