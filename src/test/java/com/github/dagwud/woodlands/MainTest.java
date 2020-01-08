package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.web.TelegramServlet;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MainTest
{
  @Test
  public void testPlayerSetup()
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    initPlayer();

    assertEquals("helloooo", gameState.getVariables().lookupVariableValue("Player.Name"));
    assertEquals("Druid", gameState.getVariables().lookupVariableValue("Player.Class"));
    assertEquals("1", gameState.getVariables().lookupVariableValue("Player.Level"));
    assertEquals("80", gameState.getVariables().lookupVariableValue("Player.HP"));
    assertEquals("100", gameState.getVariables().lookupVariableValue("Player.MaxHP"));
    assertEquals("2", gameState.getVariables().lookupVariableValue("Player.Mana"));
    assertEquals("3", gameState.getVariables().lookupVariableValue("Player.MaxMana"));
  }

  @Test
  public void testWeapon()
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    initPlayer();
    Update update = createUpdate("/village");
    new TelegramServlet().processTelegramUpdate(update);
  }

  private Update createUpdate(String messageText)
  {
    Update u = new Update();
    u.message = new Message();
    u.message.text = messageText;
    u.message.chat = new Chat();
    u.message.chat.id = -1;
    return u;
  }

  private void initPlayer()
  {
    Update update = createUpdate("/new");
    new TelegramServlet().processTelegramUpdate(update);
    // suspends to ask for player name
    update = createUpdate("helloooo");
    new TelegramServlet().processTelegramUpdate(update);
    // suspends to ask for player class
    update = createUpdate("Druid");
    new TelegramServlet().processTelegramUpdate(update);
  }

}
