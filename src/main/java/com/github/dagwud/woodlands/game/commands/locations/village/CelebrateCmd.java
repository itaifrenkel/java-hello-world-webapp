package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class CelebrateCmd extends SuspendableCmd
{
  private final int chatId;
  private final PlayerCharacter character;

  public CelebrateCmd(int chatId, PlayerState playerState)
  {
    super(playerState, 2);
    this.chatId = chatId;
    this.character = playerState.getActiveCharacter();
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    if (character.getStats().getAvailableStatsPointUpgrades() <= 0)
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "You have nothing to celebrate"));
      resetMenu(getPlayerState());
      return;
    }

    switch (phaseToExecute)
    {
      case 0:
        promptForStat();
        break;
      case 1:
        boostStat(capturedInput);
    }
  }

  private void promptForStat()
  {
    String[] stats = {"Strength", "Agility", "Constitution"};
    ChoiceCmd cmd = new ChoiceCmd(chatId, "You have " + character.getStats().getAvailableStatsPointUpgrades() + " stat point upgrades available. Which stat would you like to increase?\n" +
                      "Strength: damage with ⚔️melee weapons\n" +
                      "Agility: damage with 🏹ranged weapons\n" +
                      "         and turn order\n" +
                      "Constitution: Max ❤️ and more effective rest/heal", stats);
    CommandDelegate.execute(cmd);
  }

  private void boostStat(String capturedInput)
  {
    Stats stats = character.getStats();
    if (capturedInput.equalsIgnoreCase("Strength"))
    {
      stats.getStrength().setBase(stats.getStrength().getBase() + 1);
    }
    else if (capturedInput.equalsIgnoreCase("Agility"))
    {
      stats.getAgility().setBase(stats.getAgility().getBase() + 1);
    }
    else if (capturedInput.equalsIgnoreCase("Constitution"))
    {
      stats.getConstitution().setBase(stats.getConstitution().getBase() + 1);
      stats.getMaxHitPoints().setBase(stats.getMaxHitPoints().getBase() + stats.getLevel());
    }
    else
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "Maybe try again when you're not distracted by the elf at the bar"));
      return;
    }
    CommandDelegate.execute(new SendMessageCmd(chatId, "Congratulations!"));
    stats.setAvailableStatsPointUpgrades(stats.getAvailableStatsPointUpgrades() - 1);
  }
}
