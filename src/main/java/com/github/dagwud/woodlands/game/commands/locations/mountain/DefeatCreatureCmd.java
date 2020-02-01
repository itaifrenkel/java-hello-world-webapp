package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.GrantExperienceCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

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
    int rewardPerCharacter = Math.floorDiv(reward, victoriousParty.size());
    for (PlayerCharacter member : victoriousParty.getActivePlayerCharacters())
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
