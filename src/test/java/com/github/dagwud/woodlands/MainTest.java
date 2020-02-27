package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.character.CastSpellCmd;
import com.github.dagwud.woodlands.game.commands.character.JoinPartyCmd;
import com.github.dagwud.woodlands.game.commands.character.LevelUpCmd;
import com.github.dagwud.woodlands.game.commands.inventory.SpawnTrinketCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.ArmyOfPeasants;
import com.github.dagwud.woodlands.game.domain.characters.spells.HealingBlast;
import com.github.dagwud.woodlands.game.domain.trinkets.AmuletOfProtection;
import com.github.dagwud.woodlands.game.log.Logger;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.web.TelegramServlet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class MainTest
{
  private static int playerCount = 1;
  private SimulatorSender iMessageSender;

  @Test
  public void testPlayerSetupRequired() throws Exception
  {
    PlayerState playerState = startBot();
    processCommand(playerState, "The Inn");
  }

  @Test
  public void testHealHealthyNPC() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState, "General");

    PlayerState playerStateWizard = startBot(false);
    initPlayer(playerStateWizard);

    CommandDelegate.execute(new CastSpellCmd(new ArmyOfPeasants(playerState.getActiveCharacter())));

    int hitpoints = getPeasantPoints(playerStateWizard);

    CommandDelegate.execute(new CastSpellCmd(new HealingBlast(playerStateWizard.getActiveCharacter())));
    int newPoints = getPeasantPoints(playerStateWizard);

    Assert.assertEquals(hitpoints, newPoints);
  }

  @Test
  public void testGiveOnlyShowConsciousPlayers() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState, "General");

    PlayerState playerStateWizard = startBot(false);
    initPlayer(playerStateWizard);

    processCommand(playerState, "The Inn");
    processCommand(playerStateWizard, "The Inn");

    CommandDelegate.execute(new CastSpellCmd(new ArmyOfPeasants(playerState.getActiveCharacter())));

    processCommand(playerState, "/give");
  }

  @Test
  public void testBestParty() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState, "General");

    PlayerState playerStateWizard = startBot(false);
    initPlayer(playerStateWizard);

    CommandDelegate.execute(new JoinPartyCmd(playerStateWizard.getActiveCharacter(), "OtherParty"));

    Update update = createUpdate("/bestparty", playerState);
    new TelegramServlet().processTelegramUpdate(update);
  }

  private int getPeasantPoints(PlayerState playerState)
  {
    int hitpoints = 0;
    for (GameCharacter activeMember : playerState.getActiveCharacter().getParty().getActiveMembers())
    {
      if (activeMember instanceof Peasant)
      {
        hitpoints = activeMember.getStats().getHitPoints();
      }
    }
    return hitpoints;
  }

  @Test
  public void testPlayerSetup() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    assertEquals("TestUser" + playerState.getPlayer().getChatId(), playerState.getActiveCharacter().getName());
    assertEquals(ECharacterClass.WIZARD, playerState.getActiveCharacter().getCharacterClass());
    assertEquals(1, playerState.getActiveCharacter().getStats().getLevel());
    assertEquals(8, playerState.getActiveCharacter().getStats().getHitPoints());
    assertEquals(8, playerState.getActiveCharacter().getStats().getMaxHitPoints().total());
    assertEquals(2, playerState.getActiveCharacter().getStats().getMana());
    assertEquals(3, playerState.getActiveCharacter().getStats().getMaxMana().total());
    assertEquals(9, playerState.getActiveCharacter().getStats().getStrength().total());
    assertEquals(14 + 2, playerState.getActiveCharacter().getStats().getAgility().total());
    assertEquals(15, playerState.getActiveCharacter().getStats().getConstitution().total());

    assertEquals(ELocation.VILLAGE_SQUARE, playerState.getActiveCharacter().getLocation());
  }

  @Test
  public void testGiveItem() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    PlayerState playerState2 = startBot(false);
    initPlayer(playerState2);

    processCommand(playerState, "The Inn");
    processCommand(playerState2, "The Inn");
    processCommand(playerState, "Retrieve Items");
    processCommand(playerState, "/me");
    assertEquals(3, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(3, playerState2.getActiveCharacter().getCarrying().countTotalCarried());
    Item givenItem = playerState.getActiveCharacter().getCarrying().getCarriedInactive().get(0);

    processCommand(playerState, "/give");
    processCommand(playerState, playerState2.getActiveCharacter().getName());
    processCommand(playerState, "/g0");

    assertEquals(2, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(4, playerState2.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(givenItem, playerState2.getActiveCharacter().getCarrying().getCarriedInactive().get(0));
  }

  @Test
  public void testGiveItemNotInSameRoom() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    PlayerState playerState2 = startBot(false);
    initPlayer(playerState2);

    processCommand(playerState, "The Inn");
    processCommand(playerState, "/me");
    assertEquals(3, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(3, playerState2.getActiveCharacter().getCarrying().countTotalCarried());

    processCommand(playerState, "/give");
    processCommand(playerState, playerState2.getActiveCharacter().getName());
    processCommand(playerState, "/g0");

    assertEquals(3, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(3, playerState2.getActiveCharacter().getCarrying().countTotalCarried());
  }

  @Test
  public void testGiveItemPlayerIsCarryingTooMuch() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    PlayerState playerState2 = startBot(false);
    initPlayer(playerState2);

    processCommand(playerState, "The Inn");
    processCommand(playerState2, "The Inn");
    processCommand(playerState, "Retrieve Items");

    int maxItemsForTwo = playerState2.getActiveCharacter().determineMaxAllowedItems();
    for (int i = playerState.getActiveCharacter().getCarrying().countTotalCarried(); i < maxItemsForTwo; i++)
    {
      CommandDelegate.execute(new SpawnTrinketCmd(playerState2.getActiveCharacter(), new AmuletOfProtection()));
    }

    assertEquals(3, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(maxItemsForTwo, playerState2.getActiveCharacter().getCarrying().countTotalCarried());

    processCommand(playerState, "/give");
    processCommand(playerState, playerState2.getActiveCharacter().getName());
    processCommand(playerState, "/g0");

    assertEquals(3, playerState.getActiveCharacter().getCarrying().countTotalCarried());
    assertEquals(maxItemsForTwo, playerState2.getActiveCharacter().getCarrying().countTotalCarried());
  }

  private void processCommand(PlayerState playerState, String messageText) throws Exception
  {
    System.out.println("Saying: " + messageText);
    Update update = createUpdate(messageText, playerState);
    new TelegramServlet().processTelegramUpdate(update);
  }

  @Test
  public void testMenuBlocksDisallowedOptions() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    processCommand(playerState, "The Inn");
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());

    Update update = createUpdate("The Mountain", playerState); // not allowed to jump straig,ht
    new TelegramServlet().processTelegramUpdate(update);
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());
  }

  @Test
  public void testMenuBlocksAllowsValidOptions() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    processCommand(playerState, "The Inn");
    assertEquals(ELocation.INN, playerState.getActiveCharacter().getLocation());

    Update update = createUpdate("Village Square", playerState);
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

    processCommand(playerState, "The Tavern");
    Update update = createUpdate("Buy Drinks", playerState);
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

    CommandDelegate.execute(new LevelUpCmd(-1, playerState.getActiveCharacter()));

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
    assertEquals(5, playerState.getActiveCharacter().getStats().getHitPoints());
  }

  @Test
  @Ignore
  public void testStartInOwnParty() throws Exception
  {
    PlayerState playerState = startBot();
    initPlayer(playerState);

    assertNotNull(playerState.getActiveCharacter().getParty());
    assertEquals(1, playerState.getActiveCharacter().getParty().size());
    assertSame(playerState.getActiveCharacter(), playerState.getActiveCharacter().getParty().getActiveMembers().get(0));

    PlayerState playerState2 = startBot(false);
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

    Update joinPrompt1 = createUpdate("Join a Party", playerState);
    new TelegramServlet().processTelegramUpdate(joinPrompt1);
    Update join1 = createUpdate("junit", playerState);
    new TelegramServlet().processTelegramUpdate(join1);

    assertNotNull(playerState.getActiveCharacter().getParty());
    assertEquals(1, playerState.getActiveCharacter().getParty().size());
    assertSame(playerState.getActiveCharacter(), playerState.getActiveCharacter().getParty().getActiveMembers().get(0));

    PlayerState playerState2 = startBot(false);
    initPlayer(playerState2);

    Update jointPrompt2 = createUpdate("Join a Party", playerState2);
    new TelegramServlet().processTelegramUpdate(jointPrompt2);
    Update join2 = createUpdate(playerState.getActiveCharacter().getParty().getName(), playerState2);
    new TelegramServlet().processTelegramUpdate(join2);

    Party party = playerState.getActiveCharacter().getParty();
    assertEquals(party, playerState.getActiveCharacter().getParty());
    assertEquals(party, playerState2.getActiveCharacter().getParty());
    assertEquals(2, party.size());
    assertSame(playerState.getActiveCharacter(), party.getActiveMembers().get(0));
    assertSame(playerState2.getActiveCharacter(), party.getActiveMembers().get(1));
  }

  private PlayerState startBot() throws Exception
  {
    return startBot(true);
  }

  private PlayerState startBot(boolean reset) throws Exception
  {
    if (reset)
    {
      iMessageSender = new SimulatorSender();
      MessagingFactory.create(iMessageSender);
      GameStatesRegistry.reset();
    }

    PlayerState playerState = GameStatesRegistry.lookup(-1 * playerCount);
    playerCount++;
    processCommand(playerState, "/start");
    processCommand(playerState, "/new");
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
    initPlayer(playerState, "Wizard");
  }

  private void initPlayer(PlayerState playerState, String playerClass) throws Exception
  {
    Update update;

    update = createUpdate("TestUser" + playerState.getPlayer().getChatId(), playerState);
    Logger.info("TestUser" + playerState.getPlayer().getChatId());
    new TelegramServlet().processTelegramUpdate(update);

    // suspends to ask for player class
    update = createUpdate(playerClass, playerState);
    Logger.info(playerClass);
    new TelegramServlet().processTelegramUpdate(update);
  }

}
