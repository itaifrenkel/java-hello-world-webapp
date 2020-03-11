package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.FightingGroup;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class TooManyUnconsciousCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final FightingGroup fightingGroup;

  TooManyUnconsciousCmd(FightingGroup fightingGroup)
  {
    this.fightingGroup = fightingGroup;
  }

  @Override
  public void execute()
  {
    String message = buildMessage();
    SendPartyMessageCmd msg = new SendPartyMessageCmd(fightingGroup, message);
    CommandDelegate.execute(msg);

    for (Fighter member : fightingGroup.getActiveMembers())
    {
      DeathCmd cmd = new DeathCmd(member);
      CommandDelegate.execute(cmd);
    }
  }

  private String buildMessage()
  {
    if (this.fightingGroup.size() == 1)
    {
      return "You've been knocked unconscious, all alone in" + fightingGroup.getLeader().getLocation().getDisplayName() + ". Your body is never recovered. This is the end of your story.";
    }
    return "Suddenly, you realize there's no way for you to get back to the village. You're screwed. You can't leave your fallen comrades' bodies here, but you have no way to carry them with you. You're doomed to stay where you are - unable to leave. It's been fun, but your game is over.";
  }
}
