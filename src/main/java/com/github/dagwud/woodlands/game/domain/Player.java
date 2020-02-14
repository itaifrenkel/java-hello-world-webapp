package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private PlayerCharacter activeCharacter;
  private List<PlayerCharacter> inactiveCharacters = new ArrayList<>();

  public Player(int chatId)
  {
    this.chatId = chatId;
  }

  public int getChatId()
  {
    return chatId;
  }

  public PlayerCharacter getActiveCharacter()
  {
    return activeCharacter;
  }

  public void setActiveCharacter(PlayerCharacter activeCharacter)
  {
    this.activeCharacter = activeCharacter;
  }

  public PlayerState getPlayerState()
  {
    return GameStatesRegistry.lookup(chatId);
  }

  public List<PlayerCharacter> getInactiveCharacters()
  {
    return inactiveCharacters;
  }

  public List<PlayerCharacter> getAllCharacters()
  {
    List<PlayerCharacter> all = new ArrayList<>(getInactiveCharacters().size() + 1);
    if (getActiveCharacter() != null)
    {
      all.add(getActiveCharacter());
    }
    all.addAll(getInactiveCharacters());
    return Collections.unmodifiableList(all);
  }

  public void remove(GameCharacter character)
  {
    if (activeCharacter == character)
    {
      activeCharacter = null;
    }
    getInactiveCharacters().remove(character);
  }
}
