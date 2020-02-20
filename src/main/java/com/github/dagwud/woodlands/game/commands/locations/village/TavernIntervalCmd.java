package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.commands.core.AbstractRoomCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

import java.util.Arrays;
import java.util.List;

public class TavernIntervalCmd extends AbstractRoomCmd
{

  private List<String> getEmissions()
  {
    return Arrays.asList(
            "The speaker belts out \"%s\"",
            "\"%s\" mutters the PA system, like it's angry people aren't listening.",
            "\"%1$s\" yells the audio.  \"%1$s!\" yells back the crowd.",
            "\"%s\" echoes around the room. The crowd goes wild.",
            "The speakers bare their souls and croon \"%s\", but no-one cares.",
            "The music pauses, the crowd tenses, then the world explodes to the noise of \"%s\"",
            "The speaker belch out, mightily, singing \"%s\"",
            "Behind the crowd yelling you can make out \"%s\"",
            "The speakers ooze out, \"%s\""
    );
  }

  private List<String> getLyrics()
  {
    return Arrays.asList(
            "I wanna rock.... ROCK!",
            "Baaabbbyyyyy SHARK DO DOO DO DO DO DOOOOOO",
            "The show must go ooooooOOonnnnn!",
            "You gotta fight... for your right... to PARRRRRRRR-TAAAAAAYYYY",
            "GIMME FUEL GIMME FIRE GIVE ME THAT WHICH I DESIRE!",
            "The beautiful people...",
            "It's the.... EYE OF THE TIGER IT'S THE CREAM OF THE CROP",
            "Backstreet's back... ALRIGHT?!?!",
            "This is Scatman's world - baaa do be.",
            "And slam in the back of my dragulaaaaaa...",
            "GETTING JIGGY WIDDIT",
            "I wanna daaaaance with some body...",
            "SPICE UP YOUR LIFE!",
            "One for my baby, and one more for the road.",
            "One night in Bangkok makes the haaard man huuumble!",
            "Generals gathered in their masses, just like witches at black masses.",
            "Our tiiiime is ruuuuuuuuning outtt...",
            "Bye byeeeee Ms American Pie...",
            "The PHAAAAAANTOMMM OF THE OPERA.... IS HERRRE",
            "We don't need no, education...",
            "I fell into a ring of fire.",
            "Guess who's back? Back again?",
            "There's a lady who's sure - all that glitters is gold.",
            "You're poison running through my veins",
            "Bring server up, bring server down - hotfix live and code around",
            "I want to break freeeeee...",
            "I'm too sexy for my shirt!",
            "I'm a cat, I'm a kitty cat, and I dance dance dance and I dance dance dance...",
            "Look at my horse, my horse is amaaaazing",
            "Whole lotta love...",
            "Should I stay or should I go?",
            "Here we are now - ENTERTAIN US!",
            "And you get all the blame, but as far as I'm concerned, AA, stands for: Alcohol... is awesome",
            "Mom's spaghetti"
    );
  }

  public TavernIntervalCmd()
  {
    super(30_000);
  }

  @Override
  public void execute()
  {
    List<String> lyrics = getLyrics();
    List<String> emissions = getEmissions();

    String lyric = lyrics.get((int) Math.floor(Math.random() * lyrics.size()));
    String emit = emissions.get((int) Math.floor(Math.random() * emissions.size()));

    String result = "<i>" + String.format(emit, lyric) + "</i>";

    new SendLocationMessageCmd(ELocation.TAVERN, result).go();
  }
}
