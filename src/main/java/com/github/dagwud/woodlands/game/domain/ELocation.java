package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractRoomCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.locations.ScheduledRoomIntervalsCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.TavernIntervalCmd;
import com.github.dagwud.woodlands.game.domain.menu.*;
import com.github.dagwud.woodlands.game.domain.menu.cavern.*;
import com.github.dagwud.woodlands.game.log.Logger;

import java.util.ArrayList;
import java.util.List;

public enum ELocation
{
  VILLAGE_SQUARE("The Village", new VillageMenu(), true,
          "The bustle of the village square brings a reassuring comfort to weary adventurers - the only danger here is the prices charged by the local bartender.\n\n" +
                  "The square is dominated on the one side by the local tavern, from which you hear loud music and occasional raised voices.\n\n" +
                  "Facing it, within stumbling range, is the Inn. The innkeeper is a friend, and has offered to look after any items you may wish to leave behind while you're out on your adventures.\n\n" +
                  "To the north lies a path leading up to The Mountain, and another leading out to the Woodlands."),

  INN("The Inn", new InnMenu(), true, "You look around, and see... stuff which hasn't yet been written down."),

  TAVERN("The Tavern", new TavernMenu(), true, "The smell of spilled beer permeates the local tavern, and the floor crunches slightly underfoot as you walk across the dirt from dozens of well-worn shoes. There is a worn jukebox in the corner, belting out jammin' tunes.\n\n" +
            "The Raven is a friendly place, where all are welcome as long as they pay for their drinks. A burly guard with a dark oversized coat stands watch, and the magic-imbued rings on a chain around his neck clearly signal this is not a man to be trifled with. \n" +
            "Apart from the guard, who looks permanently annoyed, the patrons of The Raven are all smiles - with the largest being the one plastered over the face of the barman.\n\n" +
            "\"Long journey?\" he asks jovially, though something about his demeanor suggests he’s not that interested in your journey so much as how many coins are in your pocket.",
          new TavernIntervalCmd()),

  BLACKSMITH("Blacksmith", new BlacksmithMenu(), true, "The Blacksmith's shop is........ hot"),

  MOUNTAIN("The Mountain", new MountainMenu(), true, "The Mountain overlooks the Village, and is home to a variety of small creatures. As such it has become something of a proving ground, where many adventurers home their skills and practice basics.\n\n" +
          "It’s also a generally safe space as long as you’re not alone - tradition dictates that because this is a training area, any adventurer who falls in a fight will be carried back to the Village by their comrades. However, being knocked unconscious while alone does bring the risk that your body may be set upon by the inhabitants of the mountain.\n\n" +
          "As such, it’s best to travel in a group."),

  PETTING_ZOO("Petting Zoo", new PettingZooMenu(), true, "The Petting Zoo is full of cute little animals"),

  WOODLANDS("The Woodlands", new WoodlandsMenu(), false, "There's stuff to be seen in the Woodlands. Like... wood. And lands."),

  DEEP_WOODS("Deep Woods", new DeepWoodsMenu(), false, "Not much is known about the deep woods..."),

  THE_GORGE("The Gorge", new GorgeMenu(), false, "Here be dragons. Dragons can't be attacked with melee weapons - only ranged ones!"),

  CAVERN_ENTRANCE("The Cavern Entrance", new CavernEntranceMenu(), false, "The rock face is marred by a large hole - the entrance to an underground system of caverns once used by miners. The miners cleared out long ago, tellling tales of monsters in the deep."),
  CAVERN_1("The Cavern", new Cavern1Menu(), false, "You're in a cavern - it's damp."),
  CAVERN_2("The Cavern", new Cavern2Menu(), false, "You're in a cavern - it's quite dark and damp"),
  CAVERN_3("The Cavern", new Cavern3Menu(), false, "You're in a cavern - it's dark and damp"),
  CAVERN_4("The Cavern", new Cavern4Menu(), false, "You're in a cavern - it's dark and damp"),
  CAVERN_5("The Cavern", new Cavern5Menu(), false, "You're in a cavern - it's dark and damp"),
  CAVERN_6("The Cavern", new Cavern6Menu(), false, "You're in a cavern - it's dark and damp"),
  CAVERN_7("The Cavern", new Cavern7Menu(), false, "You're in a cavern - it's dark and damp"),
  CAVERN_8("The Cavern", new Cavern8Menu(), false, "You're in a cavern - it's damp, and you can hear noises coming from nearby");

  private final String displayName;
  private final GameMenu menu;
  private final boolean autoRetreat;
  private final String lookText;
  private final AbstractRoomCmd roomCmd;

  private final List<GameCharacter> charactersInRoom = new ArrayList<>();

  ELocation(String displayName, GameMenu menu, boolean autoRetreat, String lookText)
  {
    this.lookText = lookText;
    this.displayName = displayName;
    this.menu = menu;
    this.autoRetreat = autoRetreat;
    this.roomCmd = null;
  }

  ELocation(String displayName, GameMenu menu, boolean autoRetreat, String lookText, AbstractRoomCmd roomCmd)
  {
    this.displayName = displayName;
    this.menu = menu;
    this.autoRetreat = autoRetreat;
    this.lookText = lookText;

    this.roomCmd = roomCmd;
  }

  public static void scheduleRooms()
  {
    long now = System.currentTimeMillis();

    List<Tuple<Long, AbstractRoomCmd>> schedule = new ArrayList<>();

    for (ELocation value : values())
    {
      if (value.roomCmd == null)
      {
        continue;
      }

      Logger.info("Scheduling " + value.roomCmd + ", for " + value.displayName);
      schedule.add(new Tuple<>(now + value.roomCmd.getInterval(), value.roomCmd));
    }

    CommandDelegate.execute(new RunLaterCmd(1000, new ScheduledRoomIntervalsCmd(schedule), false));
  }

  public List<GameCharacter> getCharactersInRoom()
  {
    return charactersInRoom;
  }

  public String toString()
  {
    return displayName;
  }

  public GameMenu getMenu()
  {
    return menu;
  }

  public boolean isAutoRetreat()
  {
    return autoRetreat;
  }

  public String getDisplayName()
  {
    return displayName;
  }

  public String getLookText()
  {
    return lookText;
  }

  public boolean isVillageLocation()
  {
    return this == VILLAGE_SQUARE || this == INN || this == TAVERN || this == BLACKSMITH;
  }
}
