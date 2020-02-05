package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.spells.SpellAbilities;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

public abstract class Fighter extends GameObject
{
  private static final long serialVersionUID = 1L;

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
        .divide(new BigDecimal(stats.getMaxHitPoints()), 3, RoundingMode.HALF_DOWN);
    if (perc.compareTo(new BigDecimal("0.8")) >= 0)
    {
      return "💚";
    }
    if (perc.compareTo(new BigDecimal("0.65")) >= 0)
    {
      return "💛";
    }
    if (perc.compareTo(new BigDecimal("0.45")) >= 0)
    {
      return "🧡";
    }
    if (perc.compareTo(new BigDecimal("0.2")) >= 0)
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

  public boolean isDead()
  {
    return getStats().getState() == EState.DEAD;
  }

  public abstract Fighter chooseFighterToAttack(Collection<Fighter> fighters);
}
