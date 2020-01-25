package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Party;
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
  private static int playerCount = 1;

  @Test
  public void testPlayerSetupRequired() throws Exception
  {
    PlayerState playerState = startBot();
    Update update;
    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testPlayerSetup() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    assertEquals("TestUser"+playerState.getPlayer().getChatId(), playerState.getActiveCharacter().getName());
    assertEquals(ECharacterClass.WIZARD, playerState.getActiveCharacter().getCharacterClass());
    assertEquals(1, playerState.getActiveCharacter().getStats().getLevel());
    assertEquals(8, playerState.getActiveCharacter().getStats().getHitPoints());
    assertEquals(8, playerState.getActiveCharacter().getStats().getMaxHitPoints());
    assertEquals(2, playerState.getActiveCharacter().getStats().getMana());
    assertEquals(3, playerState.getActiveCharacter().getStats().getMaxMana());
    assertEquals(9, playerState.getActiveCharacter().getStats().getStrength());
    assertEquals(14 + 2, playerState.getActiveCharacter().getStats().getAgility());
    assertEquals(15, playerState.getActiveCharacter().getStats().getConstitution());

    assertEquals(ELocation.VILLAGE_SQUARE, playerState.getActiveCharacter().getLocation());
  }

  @Test
  public void testWeapon() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    Update update;
    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("Retrieve Items", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("/me", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertNull(playerState.getActiveCharacter().getCarrying().getCarriedLeft());
    assertNull(playerState.getActiveCharacter().getCarrying().getCarriedRight());
    assertEquals(1, playerState.getActiveCharacter().getCarrying().countTotalCarried());
  }

  @Test
  public void testMenuBlocksDisallowedOptions() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    Update update;
    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());

    update = createUpdate("The Mountain", playerState); // not allowed to jump straig,ht
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());
  }

  @Test
  public void testMenuBlocksAllowsValidOptions() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    Update update;
    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());

    update = createUpdate("Village Square", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.VILLAGE_SQUARE, playerState.getActiveCharacter().getLocation());

    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());
  }

  @Test
  public void testDrink() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    Update update;
    update = createUpdate("The Tavern", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("Buy Drinks", playerState);
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testDamageAndShortRest() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    Update update;
    assertEquals(8, playerState.getActiveCharacter().getStats().getHitPoints());
    assertEquals(ELocation.VILLAGE_SQUARE, playerState.getActiveCharacter().getLocation());

    update = createUpdate("The Tavern", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.TAVERN, playerState.getActiveCharacter().getLocation());

    update = createUpdate("Buy Drinks", playerState);
    while (playerState.getActiveCharacter().getStats().getHitPoints() > 5)
    {
      new TelegramServlet().processTelegramUpdate(update);
    }
    assertEquals(5, playerState.getActiveCharacter().getStats().getHitPoints());

    update = createUpdate("Village Square", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.VILLAGE_SQUARE, playerState.getActiveCharacter().getLocation());

    update = createUpdate("The Inn", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());

    update = createUpdate("Short Rest", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(8, playerState.getActiveCharacter().getStats().getHitPoints());
  }

  @Test
  public void testStartInOwnParty() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    assertNotNull(playerState.getActiveCharacter().getParty());
    assertEquals(1, playerState.getActiveCharacter().getParty().size());
    assertSame(playerState.getActiveCharacter(), playerState.getActiveCharacter().getParty().getActiveMembers().get(0));

    PlayerState playerState2 = startBot();
    initPlayer(playerState2);
    assertNotNull(playerState2.getActiveCharacter().getParty());
    assertEquals(1, playerState2.getActiveCharacter().getParty().size());
    assertSame(playerState2.getActiveCharacter(), playerState2.getActiveCharacter().getParty().getActiveMembers().get(0));

    assertEquals(1, playerState.getActiveCharacter().getParty().size());
    assertSame(playerState.getActiveCharacter(), playerState.getActiveCharacter().getParty().getActiveMembers().get(0));
  }

  @Test
  public void testJoinParty() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    assertNotNull(playerState.getActiveCharacter().getParty());
    assertEquals(1, playerState.getActiveCharacter().getParty().size());
    assertSame(playerState.getActiveCharacter(), playerState.getActiveCharacter().getParty().getActiveMembers().get(0));

    PlayerState playerState2 = startBot();
    initPlayer(playerState2);
    Update update = createUpdate("Join a Party", playerState2);
    new TelegramServlet().processTelegramUpdate(update);

    Update update2 = createUpdate(playerState.getActiveCharacter().getParty().getName(), playerState2);
    new TelegramServlet().processTelegramUpdate(update2);

    Party party = playerState.getActiveCharacter().getParty();
    assertSame(party, playerState.getActiveCharacter().getParty());
    assertSame(party, playerState2.getActiveCharacter().getParty());
    assertEquals(2, party.size());
    assertSame(playerState.getActiveCharacter(), party.getActiveMembers().get(0));
    assertSame(playerState2.getActiveCharacter(), party.getActiveMembers().get(1));
  }

  private PlayerState startBot() throws Exception
  {
    MessagingFactory.create(new SimulatorSender());

    GameStatesRegistry.reset();
    PlayerState playerState = GameStatesRegistry.lookup(-1 * playerCount);
    playerCount++;
    Update update;
    update = createUpdate("/start", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    update = createUpdate("/new", playerState);
    new TelegramServlet().processTelegramUpdate(update);
    return playerState;
  }

  private Update createUpdate(String messageText, PlayerState playerState)
  {
    Update u = new Update();
    u.message = new Message();
    u.message.text = messageText;
    u.message.chat = new Chat();
    u.message.chat.id = playerState.getPlayer().getChatId();
    return u;
  }

  private void initPlayer(PlayerState playerState) throws Exception
  {
    Update update;

    update = createUpdate("TestUser" + playerState.getPlayer().getChatId(), playerState);
    System.out.println("TestUser" + playerState.getPlayer().getChatId());
    new TelegramServlet().processTelegramUpdate(update);

    // suspends to ask for player class
    update = createUpdate("Wizard", playerState);
    System.out.println("Wizard");
    new TelegramServlet().processTelegramUpdate(update);
  }

}
