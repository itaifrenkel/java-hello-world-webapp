package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.web.TelegramServlet;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MainTest
{
  @Test
  public void testPlayerSetupRequired() throws IOException, ActionInvocationException
  {
    startBot();
    Update update;
    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testABC() throws IOException, ActionInvocationException
  {
    startBot();
    initPlayer();

    Update update;
    update = createUpdate("Retrieve Items");
    new TelegramServlet().processTelegramUpdate(update);

    update = createUpdate("/me");
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testPlayerSetup() throws IOException, ActionInvocationException
  {
    GameState gameState = startBot();
    initPlayer();

    assertEquals("TestUser", gameState.getVariables().lookupVariableValue("Player.Name"));
    assertEquals("Wizard", gameState.getVariables().lookupVariableValue("Player.Class"));
    assertEquals("1", gameState.getVariables().lookupVariableValue("Player.Level"));
    assertEquals("8", gameState.getVariables().lookupVariableValue("Player.HP"));
    assertEquals("8", gameState.getVariables().lookupVariableValue("Player.MaxHP"));
    assertEquals("2", gameState.getVariables().lookupVariableValue("Player.Mana"));
    assertEquals("3", gameState.getVariables().lookupVariableValue("Player.MaxMana"));
    assertEquals("9", gameState.getVariables().lookupVariableValue("Player.Strength"));
    assertEquals("14", gameState.getVariables().lookupVariableValue("Player.Agility"));
    assertEquals("15", gameState.getVariables().lookupVariableValue("Player.Constitution"));

    assertEquals("The Village", gameState.getVariables().lookupVariableValue("Player.Location"));
  }

  @Test
  public void testWeapon() throws IOException, ActionInvocationException
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("Retrieve Items");
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("/me");
    new TelegramServlet().processTelegramUpdate(update);
    assertNotEquals("nothing", gameState.getVariables().lookupVariableValue("Player.Item.Carrying.Left"));
    assertEquals("nothing", gameState.getVariables().lookupVariableValue("Player.Item.Carrying.Right"));
  }

  @Test
  public void testDrink() throws IOException, ActionInvocationException
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    update = createUpdate("The Tavern");
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("Buy Drinks");
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testDamageAndShortRest() throws IOException, ActionInvocationException
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    assertEquals("8", gameState.getVariables().lookupVariableValue("Player.HP"));

    update = createUpdate("Buy Drinks");
    while (Integer.parseInt(gameState.getVariables().lookupVariableValue("Player.HP")) > 5)
    {
      new TelegramServlet().processTelegramUpdate(update);
    }
    assertEquals("5", gameState.getVariables().lookupVariableValue("Player.HP"));

    update = createUpdate("Village Square");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals("The Village", gameState.getVariables().lookupVariableValue("Player.Location"));

    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals("The Inn", gameState.getVariables().lookupVariableValue("Player.Location"));

    update = createUpdate("Short Rest");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals("8", gameState.getVariables().lookupVariableValue("Player.HP"));
  }


  private GameState startBot() throws IOException, ActionInvocationException
  {
    GameStatesRegistry.reset();
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    Update update;
    update = createUpdate("/start");
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("/new");
    new TelegramServlet().processTelegramUpdate(update);
    return gameState;
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

  private void initPlayer() throws IOException, ActionInvocationException
  {
    Update update;

    update = createUpdate("TestUser");
    System.out.println("TestUser");
    new TelegramServlet().processTelegramUpdate(update);

    // suspends to ask for player class
    update = createUpdate("Wizard");
    System.out.println("Wizard");
    new TelegramServlet().processTelegramUpdate(update);
  }

}
