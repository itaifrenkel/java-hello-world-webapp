package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stat;

import java.util.Map;

public class AdminShowCharacterStatsCmd extends SuspendableCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;

  public AdminShowCharacterStatsCmd(int chatId, PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2);
    this.character = character;
    this.chatId = chatId;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForCharacter();
        break;
      case 1:
        show(capturedInput);
        break;
    }
  }

  private void promptForCharacter()
  {
    ShowPlayerChoiceCmd cmd = new ShowPlayerChoiceCmd(chatId, "Which player?", character.getParty());
    CommandDelegate.execute(cmd);
  }

  private void show(String name)
  {
    if (getPlayerState().getPlayer().getChatId() != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return;
    }

    for (Party party : PartyRegistry.listAllParties())
    {
      for (GameCharacter gameCharacter : party.getActivePlayerCharacters())
      {
        if (gameCharacter instanceof PlayerCharacter)
        {
          PlayerCharacter character = (PlayerCharacter)gameCharacter;
          if (character.getName().equalsIgnoreCase(name))
          {
            showStats(character);
          }
        }
      }
    }
  }

  private void showStats(PlayerCharacter character)
  {
    StringBuilder b = new StringBuilder();
    b.append("<b>Stats for ").append(character.getName()).append(":</b>\n");
    b.append(buildStat("Level", character.getStats().getLevel())).append("\n");
    b.append(buildStat("HitPoints", character.getStats().getHitPoints())).append("\n");
    b.append(buildStat("MaxHitPoints", character.getStats().getMaxHitPoints())).append("\n");
    b.append(buildStat("Mana", character.getStats().getMana())).append("\n");
    b.append(buildStat("MaxMana", character.getStats().getMaxMana())).append("\n");
    b.append(buildStat("Strength", character.getStats().getStrength())).append("\n");
    b.append(buildStat("Agility", character.getStats().getAgility())).append("\n");
    b.append(buildStat("AgilityModifier", character.getStats().getAgilityModifier())).append("\n");
    b.append(buildStat("Constitution", character.getStats().getConstitution())).append("\n");
    b.append(buildStat("WeaponBonusHit", character.getStats().getWeaponBonusHits())).append("\n");
    b.append(buildStat("BonusDamage", character.getStats().getBonusDamage())).append("\n");
    b.append(buildStat("WeaponBonusDamage", character.getStats().getWeaponBonusDamage())).append("\n");
    b.append(buildStat("State", character.getStats().getState().name())).append("\n");
    b.append(buildStat("Drunkeness", character.getStats().getDrunkeness())).append("\n");
    b.append(buildStat("Experience", character.getStats().getExperience())).append("\n");
    b.append(buildStat("CriticalStrikeChanceBonus", character.getStats().getCriticalStrikeChanceBonus())).append("\n");
    b.append(buildStat("RestPoints", character.getStats().getRestPoints())).append("\n");
    b.append(buildStat("RestPointsMax", character.getStats().getRestPointsMax())).append("\n");
    b.append(buildStat("RestDiceFace", character.getStats().getRestDiceFace())).append("\n");
    b.append(buildStat("ConstitutionModifier", character.getStats().getConstitutionModifier())).append("\n");
    b.append(buildStat("HitBoost", character.getStats().getHitBoost())).append("\n");
    b.append(buildStat("DamageMultiplier", character.getStats().getDamageMultiplier())).append("\n");
    b.append(buildStat("DefenceRating", character.getStats().getDefenceRating())).append("\n");
    b.append(buildStat("DefenceRatingBoost", character.getStats().getDefenceRatingBoost())).append("\n");
    b.append(buildStat("AvailableStatsPointUpgrades", character.getStats().getAvailableStatsPointUpgrades())).append("\n");
    b.append(buildStat("HitPointsDescription", character.getStats().getHitPointsDescription())).append("\n");
    b.append(buildStat("EnchantmentsCount", character.getStats().getEnchantmentsCount())).append("\n");
    b.append(buildStat("CraftsCount", character.getStats().getCraftsCount())).append("\n");
    b.append(buildStat("ItemsGivenAwayCount", character.getStats().getItemsGivenAwayCount())).append("\n");
    b.append(buildStat("ItemsDroppedCount", character.getStats().getItemsDroppedCount())).append("\n");
    b.append(buildStat("LeadershipMovesCount", character.getStats().getLeadershipMovesCount())).append("\n");

    CommandDelegate.execute(new SendAdminMessageCmd(b.toString()));
  }

  private String buildStat(String statName, Map<String, Integer> weaponMap)
  {
    StringBuilder b = new StringBuilder();
    for (Map.Entry<String, Integer> weaponBonus : weaponMap.entrySet())
    {
      if (b.length() > 0)
      {
        b.append("\n");
      }
      b.append(buildStat(statName + " " + weaponBonus.getKey(), weaponBonus.getValue()));
    }
    return b.toString();
  }

  private String buildStat(String statName, Stat statValue)
  {
    return statName + ": " + statValue.getBase() + "+" + statValue.getBonus();
  }

  private String buildStat(String statName, Number statValue)
  {
    return statName + ": " + statValue;
  }

  private String buildStat(String statName, String statValue)
  {
    return statName + ": " + statValue;
  }
}
