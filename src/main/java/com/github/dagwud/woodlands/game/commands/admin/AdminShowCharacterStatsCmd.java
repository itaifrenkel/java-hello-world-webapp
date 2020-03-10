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
      for (GameCharacter gameCharacter : party.getAllMembers())
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
    b.append(buildStat("Level", character.getStats().getLevel()));
    b.append(buildStat("HitPoints", character.getStats().getHitPoints()));
    b.append(buildStat("MaxHitPoints", character.getStats().getMaxHitPoints()));
    b.append(buildStat("Mana", character.getStats().getMana()));
    b.append(buildStat("MaxMana", character.getStats().getMaxMana()));
    b.append(buildStat("Strength", character.getStats().getStrength()));
    b.append(buildStat("Agility", character.getStats().getAgility()));
    b.append(buildStat("AgilityModifier", character.getStats().getAgilityModifier()));
    b.append(buildStat("Constitution", character.getStats().getConstitution()));
    b.append(buildStat("WeaponBonusHit", character.getStats().getWeaponBonusHits()));
    b.append(buildStat("BonusDamage", character.getStats().getBonusDamage()));
    b.append(buildStat("WeaponBonusDamage", character.getStats().getWeaponBonusDamage()));
    b.append(buildStat("State", character.getStats().getState().name()));
    b.append(buildStat("Drunkeness", character.getStats().getDrunkeness()));
    b.append(buildStat("Experience", character.getStats().getExperience()));
    b.append(buildStat("CriticalStrikeChanceBonus", character.getStats().getCriticalStrikeChanceBonus()));
    b.append(buildStat("RestPoints", character.getStats().getRestPoints()));
    b.append(buildStat("RestPointsMax", character.getStats().getRestPointsMax()));
    b.append(buildStat("RestDiceFace", character.getStats().getRestDiceFace()));
    b.append(buildStat("ConstitutionModifier", character.getStats().getConstitutionModifier()));
    b.append(buildStat("HitBoost", character.getStats().getHitBoost()));
    b.append(buildStat("DamageMultiplier", character.getStats().getDamageMultiplier()));
    b.append(buildStat("DefenceRating", character.getStats().getDefenceRating()));
    b.append(buildStat("DefenceRatingBoost", character.getStats().getDefenceRatingBoost()));
    b.append(buildStat("AvailableStatsPointUpgrades", character.getStats().getAvailableStatsPointUpgrades()));
    b.append(buildStat("HitPointsDescription", character.getStats().getHitPointsDescription()));
    b.append(buildStat("EnchantmentsCount", character.getStats().getEnchantmentsCount()));
    b.append(buildStat("CraftsCount", character.getStats().getCraftsCount()));
    b.append(buildStat("ItemsGivenAwayCount", character.getStats().getItemsGivenAwayCount()));
    b.append(buildStat("ItemsDroppedCount", character.getStats().getItemsDroppedCount()));
    b.append(buildStat("LeadershipMovesCount", character.getStats().getLeadershipMovesCount()));

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
