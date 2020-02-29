package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DrunkUpMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class SpeakCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter speaker;
  private String message;

  public SpeakCmd(GameCharacter speaker, String message)
  {
    this.speaker = speaker;
    this.message = message;
  }

  @Override
  public void execute()
  {
    String speakerName = speaker.getName();

    DrunkUpMessageCmd drunkify = new DrunkUpMessageCmd(message, speaker.getStats().getDrunkeness());
    CommandDelegate.execute(drunkify);
    message = drunkify.getMessage();

    if (speaker.isResting())
    {
      message = produceDreamSpeak();
    }
    if (speaker.isDead())
    {
      speakerName = "The ghost of " + speakerName;
    }
    message = "<b>" + speakerName + " says \"" + message + "\"</b>";
    CommandDelegate.execute(new SendPartyMessageCmd(speaker.getParty(), message));
    CommandDelegate.execute(new SendPartyAlertCmd(speaker.getParty(), message));
  }

  private String produceDreamSpeak()
  {
    String[] dreams = {"goblins", "mountain", "village", "orcs", 
        "monster", "druid", "wizard", "potion", "warlord", 
        "dragon", "army", "pheasant", "herbivore",
        "broadsword", "robe", "raisins", "tavern", "ogre", 
        "cyclops", "battle axe", "goat", "witch", 
        "lion", "evil", "demon", "golem"};
    int a = (int)(Math.random() * dreams.length);
    int b = (int)(Math.random() * dreams.length);
    int c = (int)(Math.random() * dreams.length);
    return ".." + dreams[a] + "..." + dreams[b] + "..." + dreams[c] + "..";
  }
}
