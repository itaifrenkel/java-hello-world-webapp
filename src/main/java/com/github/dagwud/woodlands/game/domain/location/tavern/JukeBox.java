package com.github.dagwud.woodlands.game.domain.location.tavern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * jukebox (n.)
 * also juke-box, "machine that automatically plays selected recorded music when a coin is inserted," 1939,
 * earlier jook organ (1937), from jook joint "roadhouse, brothel" (1935), African-American vernacular,
 * from juke, joog "wicked, disorderly," a word in Gullah (the creolized English of the coastlands
 * of South Carolina, Georgia, and northern Florida).
 * <p>
 * This is probably from an African source, such as Wolof and Bambara dzug "unsavory."
 * The adjective is said to have originated in central Florida (see "A Note on Juke," Florida
 * Review, vol. vii, no. 3, spring 1938). The spelling with a -u- might represent a
 * deliberate attempt to put distance between the word and its origins.
 * <p>
 * For a long time the commercial juke trade resisted the name juke box and even tried to raise a
 * big publicity fund to wage a national campaign against it, but "juke box" turned out to be the
 * biggest advertising term that could ever have been invented for the commercial phonograph and
 * spread to the ends of the world during the war as American soldiers went abroad but remembered
 * the juke boxes back home. ["Billboard," Sept. 15, 1945]
 */
public class JukeBox implements Serializable
{
  private List<String> emissions;
  private List<String> lyrics;

  private List<String> getInitialEmissions()
  {
    return new ArrayList<>(Arrays.asList(
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
            "Something in your head yells \"%s\" - oh, it's just the speaker.",
            "The music pauses, the crowd tenses, then the world explodes to the noise of \"%s\"",
            "The speakers belch out, mightily, singing \"%s\"",
            "Behind the crowd yelling you can make out \"%s\"",
            "The speakers ooze out, \"%s\""
    ));
  }

  private List<String> getInitialLyrics()
  {
    return new ArrayList<>(Arrays.asList(
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
    ));
  }

  public List<String> getEmissions()
  {
    if (emissions == null)
    {
      emissions = getInitialEmissions();
    }

    return emissions;
  }

  public List<String> getLyrics()
  {
    if (lyrics == null)
    {
      lyrics = getInitialLyrics();
    }

    return lyrics;
  }
}
