package com.github.dagwud.woodlands.game.commands.core;

import java.io.Serializable;

public interface CommandPrerequisite extends Serializable
{
   boolean verify();
}
