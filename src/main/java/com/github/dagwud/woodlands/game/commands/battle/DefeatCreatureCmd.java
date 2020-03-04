package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.events.CreatureDefeatedEvent;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class DefeatCreatureCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Party victoriousParty;
  private final Creature creatureDefeated;
  private final boolean isFarmedEncounter;
  private final boolean isMultiCreatureEncounter;
  private int experienceGrantedPerPlayer;

  DefeatCreatureCmd(Party victoriousParty, Creature creatureDefeated, boolean isFarmedEncounter, boolean isMultiCreatureEncounter)
  {
    this.victoriousParty = victoriousParty;
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
      CommandDelegate.execute(new SendPartyMessageCmd(victoriousParty, "<b>Double XP awarded!</b>"));
    }

    DefeatCreatureRewardCmd rewardCmd = new DefeatCreatureRewardCmd(victoriousParty, creatureDefeated, isFarmedEncounter);
    CommandDelegate.execute(rewardCmd);

    List<PlayerCharacter> victoriousPlayers = findVictors(victoriousParty, creatureDefeated, isMultiCreatureEncounter);
    int rewardPerCharacter = victoriousPlayers.isEmpty() ? 0 : Math.floorDiv(reward, victoriousPlayers.size());
    for (PlayerCharacter member : victoriousPlayers)
    {
      EEvent.CREATURE_DEFEATED.trigger(new CreatureDefeatedEvent(member, creatureDefeated, rewardPerCharacter));

      GrantExperienceCmd cmd = new GrantExperienceCmd(member, rewardPerCharacter);
      CommandDelegate.execute(cmd);
    }
    experienceGrantedPerPlayer = rewardPerCharacter;
  }

  private List<PlayerCharacter> findVictors(Party party, Creature defeated, boolean isMultiCreatureEncounter)
  {
    List<PlayerCharacter> victors = new ArrayList<>();
    for (GameCharacter c : party.getActiveMembers())
    {
      if (c instanceof PlayerCharacter)
      {
        PlayerCharacter p = (PlayerCharacter) c;
        if (p.isActive() && !p.isDead())
        {
          if (isMultiCreatureEncounter || p.shouldGainExperienceByDefeating(defeated))
          {
            victors.add(p);
          }
          else
          {
            SendMessageCmd msg = new SendMessageCmd(p, "That's not exactly a fair fight; you don't qualify for an experience boost for defeating " + defeated.getName() + " (L" + defeated.difficulty + ")");
            CommandDelegate.execute(msg);
          }
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
