package com.github.dagwud.woodlands.game.domain.location.cavern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleEntendres implements Serializable
{
  List<String> entryQuips;
  List<String> exitQuips;

  public List<String> getEntryQuips()
  {
    if (entryQuips == null)
    {
      entryQuips = buildEntryQuips();
    }
    return entryQuips;
  }

  private List<String> buildEntryQuips()
  {
    return new ArrayList<>(Arrays.asList(
            "That's what she said...",
            "Harder!",
            "Spank my ass!",
            "Pull my hair!",
            "I'm givin' her all she's got Capt'n!",
            "Faster!"
    ));
  }

  public List<String> getExitQuips()
  {
    if (exitQuips == null)
    {
      exitQuips = buildExitQuips();
    }
    return exitQuips;
  }

  private List<String> buildExitQuips()
  {
    return new ArrayList<>(Arrays.asList(
            "Don't stop now!",
            "Tease...",
            "No, go deeper!",
            "Deeper!"
    ));
  }
}
