package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.io.Serializable;

public class CharacterClass implements Serializable
{
  private static final long serialVersionUID = 1L;

  public String name;

  public Variables stats;
}
