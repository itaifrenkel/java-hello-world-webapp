package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.spells.SpellAbilities;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

public abstract class Fighter extends GameObject
{
  private static final long serialVersionUID = 1L;
  private static final BigDecimal EIGHTY_PER_CENT = new BigDecimal("0.8");
  private static final BigDecimal SIXTY_FIVE_PER_CENT = new BigDecimal("0.65");
  private static final BigDecimal FORTY_PER_CENT = new BigDecimal("0.45");
  private static final BigDecimal TWENTY_PER_CENT = new BigDecimal("0.2");

  private SpellAbilities spellAbilities;

  public abstract Stats getStats();

  public abstract CarriedItems getCarrying();

  public String summary()
  {
    return summary(true);
  }

  public String summary(boolean showName)
  {
    String name = (showName ? getName() + ": " : "");
    Stats stats = getStats();
    if (stats.getState() == EState.DEAD)
    {
      return name + "☠️dead";
    }
    String message = name + healthIcon(stats) + stats.getHitPoints() + " / " + stats.getMaxHitPoints();
    if (stats.getMaxMana().total() != 0)
    {
      message += ", ✨" + stats.getMana() + "/" + stats.getMaxMana();
    }
    return message;
  }

  private String healthIcon(Stats stats)
  {
    BigDecimal perc = new BigDecimal(stats.getHitPoints())
        .divide(new BigDecimal(stats.getMaxHitPoints().total()), 3, RoundingMode.HALF_DOWN);
    if (perc.compareTo(EIGHTY_PER_CENT) >= 0)
    {
      return "💚";
    }
    if (perc.compareTo(SIXTY_FIVE_PER_CENT) >= 0)
    {
      return "💛";
    }
    if (perc.compareTo(FORTY_PER_CENT) >= 0)
    {
      return "🧡";
    }
    if (perc.compareTo(TWENTY_PER_CENT) >= 0)
    {
      return "❤️";
    }
    return "💔";
  }

  public SpellAbilities getSpellAbilities()
  {
    if (spellAbilities == null)
    {
      spellAbilities = new SpellAbilities();
    }
    return spellAbilities;
  }

  public boolean isConscious()
  {
    return getStats().getState() == EState.ALIVE;
  }

  public village isDrinking()
  {
    return getStats().getState() == EState.DRINKING;

  public boolean isDead()
  {
    return getStats().getState() == EState.DEAD;
  }

  public boolean isResting()
  {
    return getStats().getState() == EState.SHORT_RESTING ||
        getStats().getState() == EState.LONG_RESTING;
  }

  public abstract Fighter chooseFighterToAttack(Collection<Fighter> fighters);

  public final boolean canCarryMore()
  {
    return getCarrying().countTotalCarried() < determineMaxAllowedItems();
  }

  protected int determineMaxAllowedItems()
  {
    return Integer.MAX_VALUE;
  }
}
