package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

@SuppressWarnings("unused") // called at runtime via reflection
public class SpawnItemAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState, Variables callDetails)
  {
    String chatId = gameState.getVariables().lookupVariableValue("chatId");
    List<Weapon> allWeapons = ItemsCacheFactory.instance().getCache().getWeapons();
    int rand = (int) (Math.random() * allWeapons.size());
    Weapon chosenWeapon = allWeapons.get(rand);

    Variables result = new Variables();
    result.put("SpawnedItem", chosenWeapon.name);
    return new InvocationResults(result);
  }
}
