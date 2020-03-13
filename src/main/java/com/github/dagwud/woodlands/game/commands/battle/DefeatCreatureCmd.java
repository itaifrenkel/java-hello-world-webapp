package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.events.CreatureDefeatedEvent;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class DefeatCreatureCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final FightingGroup victors;
  private final Creature creatureDefeated;
  private final boolean isFarmedEncounter;
  private final boolean isMultiCreatureEncounter;
  private int experienceGrantedPerPlayer;

  DefeatCreatureCmd(FightingGroup victors, Creature creatureDefeated, boolean isFarmedEncounter, boolean isMultiCreatureEncounter)
  {
    this.victors = victors;
    this.creatureDefeated = creatureDefeated;
    this.isFarmedEncounter = isFarmedEncounter;
    this.isMultiCreatureEncounter = isMultiCreatureEncounter;
  }

  @Override
  public void execute()
  {
    double difficultyLevel = creatureDefeated.difficulty;
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(difficultyLevel);
    int reward = difficulty.experienceReward;

    if (!isFarmedEncounter)
    {
      reward = reward * 2;
      CommandDelegate.execute(new SendPartyMessageCmd(victors, "<b>Double XP awarded!</b>"));
    }

    if (victors instanceof Party)
    {
      CommandDelegate.execute(new DefeatCreatureRewardCmd((Party)victors, creatureDefeated, isFarmedEncounter));
    }

    List<PlayerCharacter> victoriousPlayers = findVictoriousPlayers(victors, creatureDefeated, isMultiCreatureEncounter);
    int rewardPerCharacter = victoriousPlayers.isEmpty() ? 0 : Math.floorDiv(reward, victoriousPlayers.size());
    for (PlayerCharacter member : victoriousPlayers)
    {
      member.getStats().incrementCreaturesDefeatedCount();

      EEvent.CREATURE_DEFEATED.trigger(new CreatureDefeatedEvent(member, creatureDefeated, rewardPerCharacter));

      GrantExperienceCmd cmd = new GrantExperienceCmd(member, rewardPerCharacter);
      CommandDelegate.execute(cmd);
    }
    experienceGrantedPerPlayer = rewardPerCharacter;
  }

  private List<PlayerCharacter> findVictoriousPlayers(FightingGroup party, Creature defeated, boolean isMultiCreatureEncounter)
  {
    List<PlayerCharacter> victors = new ArrayList<>();
    for (PlayerCharacter c : party.getActivePlayerCharacters())
    {
      if (!c.isDead())
      {
        if (isMultiCreatureEncounter || c.shouldGainExperienceByDefeating(defeated))
        {
          victors.add(c);
        }
        else
        {
          SendMessageCmd msg = new SendMessageCmd(c, "That's not exactly a fair fight; you don't qualify for an experience boost for defeating " + defeated.getName() + " (L" + defeated.difficulty + ")");
          CommandDelegate.execute(msg);
        }
      }
    }
    return victors;
  }

  public int getExperienceGrantedPerPlayer()
  {
    return experienceGrantedPerPlayer;
  }
}
