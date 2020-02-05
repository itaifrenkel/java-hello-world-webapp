package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
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
  private int experienceGrantedPerPlayer;

  DefeatCreatureCmd(Party victoriousParty, Creature creatureDefeated)
  {
    this.victoriousParty = victoriousParty;
    this.creatureDefeated = creatureDefeated;
  }

  @Override
  public void execute()
  {
    double difficultyLevel = creatureDefeated.difficulty;
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(difficultyLevel);
    int reward = difficulty.experienceReward;

    List<PlayerCharacter> victoriousPlayers = findVictors(victoriousParty, creatureDefeated);
    int rewardPerCharacter = Math.floorDiv(reward, victoriousPlayers.size());
    for (PlayerCharacter member : victoriousPlayers)
    {
      GrantExperienceCmd cmd = new GrantExperienceCmd(member, rewardPerCharacter);
      CommandDelegate.execute(cmd);
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
        if (p.isActive())
        {
          int levelDiff = p.getStats().getLevel() - defeated.getStats().getLevel();
          if (levelDiff < 5)
          {
            SendMessageCmd msg = new SendMessageCmd(p.getPlayedBy().getChatId(), "That's not exactly a fair fight; you don't qualify for an experience boost for defeating " + defeated.getName() + " (L" + defeated.getStats().getLevel() + ")");
            CommandDelegate.execute(msg); 
          }
          else
          {
            victors.add(p);
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
