package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.web.TelegramServlet;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class MainTest
{
  @Test
  public void testPlayerSetupRequired() throws Exception
  {
    startBot();
    Update update;
    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testPlayerSetup() throws Exception
  {
    GameState gameState = startBot();
    initPlayer();

    assertEquals("TestUser", gameState.getActiveCharacter().getName());
    assertEquals(ECharacterClass.WIZARD, gameState.getActiveCharacter().getCharacterClass());
    assertEquals(1, gameState.getActiveCharacter().getStats().getLevel());
    assertEquals(8, gameState.getActiveCharacter().getStats().getHitPoints());
    assertEquals(8, gameState.getActiveCharacter().getStats().getMaxHitPoints());
    assertEquals(2, gameState.getActiveCharacter().getStats().getMana());
    assertEquals(3, gameState.getActiveCharacter().getStats().getMaxMana());
    assertEquals(9, gameState.getActiveCharacter().getStats().getStrength());
    assertEquals(14, gameState.getActiveCharacter().getStats().getAgility());
    assertEquals(15, gameState.getActiveCharacter().getStats().getConstitution());

    assertEquals(ELocation.VILLAGE_SQUARE, gameState.getActiveCharacter().getLocation());
  }

  @Test
  public void testWeapon() throws Exception
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
    assertNotNull(gameState.getActiveCharacter().getCarrying().getCarriedLeft());
    assertNull(gameState.getActiveCharacter().getCarrying().getCarriedRight());
  }

  @Test
  public void testMenuBlocksDisallowedOptions() throws Exception
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, gameState.getActiveCharacter().getLocation());

    update = createUpdate("The Mountain"); // not allowed to jump straight
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, gameState.getActiveCharacter().getLocation());
  }

  @Test
  public void testMenuBlocksAllowsValidOptions() throws Exception
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, gameState.getActiveCharacter().getLocation());

    update = createUpdate("Village Square");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.VILLAGE_SQUARE, gameState.getActiveCharacter().getLocation());

    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, gameState.getActiveCharacter().getLocation());
  }

  @Test
  public void testDrink() throws Exception
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
  public void testDamageAndShortRest() throws Exception
  {
    GameState gameState = startBot();
    initPlayer();

    Update update;
    assertEquals(8, gameState.getActiveCharacter().getStats().getHitPoints());
    assertEquals(ELocation.VILLAGE_SQUARE, gameState.getActiveCharacter().getLocation());

    update = createUpdate("The Tavern");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.TAVERN, gameState.getActiveCharacter().getLocation());

    update = createUpdate("Buy Drinks");
    while (gameState.getActiveCharacter().getStats().getHitPoints() > 5)
    {
      new TelegramServlet().processTelegramUpdate(update);
    }
    assertEquals(5, gameState.getActiveCharacter().getStats().getHitPoints());

    update = createUpdate("Village Square");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.VILLAGE_SQUARE, gameState.getActiveCharacter().getLocation());

    update = createUpdate("The Inn");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, gameState.getActiveCharacter().getLocation());

    update = createUpdate("Short Rest");
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(8, gameState.getActiveCharacter().getStats().getHitPoints());
  }


  private GameState startBot() throws Exception
  {
    MessagingFactory.create(new SimulatorSender());

    GameStatesRegistry.reset();
    GameState gameState = GameStatesRegistry.lookup(-1);
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

  private void initPlayer() throws Exception
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
