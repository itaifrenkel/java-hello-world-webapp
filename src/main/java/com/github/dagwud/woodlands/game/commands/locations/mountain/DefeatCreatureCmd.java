package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

public class DefeatCreatureCmd extends AbstractCmd
{
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
    int difficultyLevel = creatureDefeated.difficulty;
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(difficultyLevel);
    int reward = difficulty.experienceReward;
    int rewardPerCharacter = Math.floorDiv(reward, victoriousParty.getMembers().size());
    for (GameCharacter member : victoriousParty.getMembers())
    {
      GrantExperienceCmd cmd = new GrantExperienceCmd(member, rewardPerCharacter);
      CommandDelegate.execute(cmd);
    }
    experienceGrantedPerPlayer = rewardPerCharacter;
  }

  public int getExperienceGrantedPerPlayer()
  {
    return experienceGrantedPerPlayer;
  }
}
