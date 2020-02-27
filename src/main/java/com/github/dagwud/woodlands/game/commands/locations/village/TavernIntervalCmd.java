package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
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
            "Feel the bass, raise your hands, the music cries \"%s\"",
            "People in the room tap their feet as the music goes \"%s\"",
            "Everyone sings to the music as it goes \"%s\"",
            "The speakers let out a burst of static, but you're pretty sure they meant \"%s\"",
            "People wave their hands in the air, and sing \"%s\" like they just don't care.",
            "The speakers bare their souls and croon \"%s\", but no-one cares.",
            "The music pauses, the crowd tenses, then the world explodes to the noise of \"%s\"",
            "The speakers belch out, mightily, singing \"%s\"",
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
            "Heyyyy mister deejay, puuuuut a record on...",
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
            "My.... milkshake brings all the boys to the yard.",
            "I fell into a ring of fire.",
            "Guess who's back? Back again?",
            "Pick yourself up and try again (try again)",
            "Don't goooo chaaaasssing waterfaaaallllsss...",
            "MAKING PANCAKES, MAKIN' BACON PAAAAAANCAKES",
            "Ceeeeeeeeelleebrate good times - COME ON!",
            "The toxicity of our ciiiity, of ouuuuurrr ciiiyyiiity...",
            "Don't go chasing waterfalls...",
            "Code monkey, get up, get coffee...",
            "Maya heeee, maya haaaa, maya heyaaheyaaa",
            "YOU AIN'T NOTHING BUT A HOUND-DAWWWWG",
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

    CommandDelegate.execute(new SendLocationMessageCmd(ELocation.TAVERN, result));
  }
}
