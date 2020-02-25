package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

import java.util.List;
import java.util.ArrayList;

public class DefeatCreatureCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Party victoriousParty;
  private final Creature creatureDefeated;
  private final boolean isFarmedEncounter;
  private int experienceGrantedPerPlayer;

  DefeatCreatureCmd(Party victoriousParty, Creature creatureDefeated, boolean isFarmedEncounter)
  {
    this.victoriousParty = victoriousParty;
    this.creatureDefeated = creatureDefeated;
    this.isFarmedEncounter = isFarmedEncounter;
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

    List<PlayerCharacter> victoriousPlayers = findVictors(victoriousParty, creatureDefeated);
    int rewardPerCharacter = victoriousPlayers.isEmpty() ? 0 : Math.floorDiv(reward, victoriousPlayers.size());
    for (PlayerCharacter member : victoriousPlayers)
    {
      GrantExperienceCmd cmd = new GrantExperienceCmd(member, rewardPerCharacter);
      CommandDelegate.execute(cmd);

      SendMessageCmd msg = new SendMessageCmd(member.getPlayedBy().getChatId(), creatureDefeated.name + " has been defeated! You gain " + rewardPerCharacter + " experience");
      CommandDelegate.execute(msg);

      //todo should probably be a command
      member.getRecentlyDefeated().add(creatureDefeated);
      while (member.getRecentlyDefeated().size() > 10)
      {
        member.getRecentlyDefeated().remove(0);
      }
    }
    experienceGrantedPerPlayer = rewardPerCharacter;
  }

  private List<PlayerCharacter> findVictors(Party party, Creature defeated)
  {
    List<PlayerCharacter> victors = new ArrayList<>();
    for (GameCharacter c : party.getActiveMembers())
    {
      if (c instanceof PlayerCharacter)
      {
        PlayerCharacter p = (PlayerCharacter)c;
        if (p.isActive() && !p.isDead())
        {
          if (p.shouldGainExperienceByDefeating(defeated))
          {
            victors.add(p);
          }
          else
          {
            SendMessageCmd msg = new SendMessageCmd(p.getPlayedBy().getChatId(), "That's not exactly a fair fight; you don't qualify for an experience boost for defeating " + defeated.getName() + " (L" + defeated.difficulty + ")");
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
