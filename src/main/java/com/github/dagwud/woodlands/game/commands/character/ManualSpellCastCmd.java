package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManualSpellCastCmd extends SuspendableCmd
{
  private final int chatId;
  private final PlayerCharacter caster;
  private final String[] spells;

  public ManualSpellCastCmd(int chatId, PlayerCharacter caster)
  {
    super(caster.getPlayedBy().getPlayerState(), 2);
    this.chatId = chatId;
    this.caster = caster;
    this.spells = produceSpells();
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    switch (phaseToExecute)
    {
      case 0:
        promptForSpell();
        break;
      case 1:
        castSpell(capturedInput);
        break;
    }
  }

  private void promptForSpell()
  {
    ChoiceCmd choice = new ChoiceCmd(chatId, "Which spell would you like to cast?", spells);
    CommandDelegate.execute(choice);
  }

  private void castSpell(String capturedInput)
  {
    for (SingleCastSpell spell : caster.getSpellAbilities().getKnownActiveSpell())
    {
      if (spell.getSpellName().equalsIgnoreCase(capturedInput))
      {
        castSpell(spell);
        return;
      }
    }
  }

  private void castSpell(SingleCastSpell spell)
  {
    CastSpellCmd cmd = new CastSpellCmd(spell);
    CommandDelegate.execute(cmd);
  }

  private String[] produceSpells()
  {
    List<String> spells = new ArrayList<>();
    List<SingleCastSpell> knownActiveSpell = caster.getSpellAbilities().getKnownActiveSpell();
    for (SingleCastSpell spell : knownActiveSpell)
    {
      if (spell.canCastOutsideOfBattle())
      {
        spells.add(spell.getSpellName());
      }
    }
    return spells.toArray(new String[0]);
  }
}
