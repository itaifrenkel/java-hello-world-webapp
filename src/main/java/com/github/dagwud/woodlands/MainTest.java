package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;

import java.util.HashMap;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    ActionInvokerDelegate.invoke("ReadPlayerName", new HashMap<String, String>());
  }

}
