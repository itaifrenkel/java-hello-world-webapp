package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.ShowCharacterInfoCmd;
import com.github.dagwud.woodlands.game.commands.battle.DeathCmd;
import com.github.dagwud.woodlands.game.commands.core.ChoiceCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.inventory.InventoryCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;
import java.util.ArrayList;

public class ShowPlayerChoiceCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final Party party;
  private final String prompt;

  public ShowPlayerChoiceCmd(int chatId, String prompt, Party party)
  {
    this.party = party;
    this.chatId = chatId;
    this.prompt = prompt;
  }

  @Override
  public void execute()
  {
    ChoiceCmd cmd = new ChoiceCmd(chatId, prompt, buildCharacterNames());
    CommandDelegate.execute(cmd);
  }

  private String[] buildCharacterNames()
  {
    List<String> characters = new ArrayList<>();
    for (GameCharacter c : party.getActivePlayerCharacters())
    {
      characters.add(c.getName());
    }
    return characters.toArray(new String[0]);
  }
}
