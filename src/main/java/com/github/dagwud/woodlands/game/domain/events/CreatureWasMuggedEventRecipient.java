package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.Collection;

public class CreatureWasMuggedEventRecipient implements EventRecipient<CreatureDroppedItemEvent>
{
  @Override
  public void trigger(CreatureDroppedItemEvent event)
  {
    if (creatureWasMugged(event.getPlayerCharacter().getParty(), event.getCreature()))
    {
      CommandDelegate.execute(new UnlockAchievementCmd(event.getPlayerCharacter(), EAchievement.MUGGED_A_CREATURE));
    }
  }

  private boolean creatureWasMugged(Party viciousParty, Creature createdDefeated)
  {
    Collection<PlayerCharacter> players = viciousParty.getActivePlayerCharacters();
    for (PlayerCharacter c : players)
    {
      if (c.shouldGainExperienceByDefeating(createdDefeated))
      {
        return false;
      }
    }

    return true;
  }
}
