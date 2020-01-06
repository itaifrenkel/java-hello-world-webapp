package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.github.dagwud.woodlands.gson.telegram.Chat;
import com.github.dagwud.woodlands.gson.telegram.Message;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.web.TelegramServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.HashMap;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    TelegramServletMock s = new TelegramServletMock();
    s.doPost(buildMessage("/new"), null);

/*
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    CallDetails callDetails = new CallDetails(new HashMap<>(), new ParamMappings());

    InvocationPlan plan = ActionInvocationPlanner.plan("PlayerSetup", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
    // suspends to ask for player text:

    plan.getGameState().getVariables().setValue("__buffer", "helloooo");
    ActionInvocationPlanExecutor.resume(plan);

    plan.getGameState().getVariables().setValue("__buffer", "Druid");
    ActionInvocationPlanExecutor.resume(plan);
*/
  }

  private static HttpServletRequest buildMessage(String s)
  {
    Update u = new Update();
    u.message = new Message();
    u.message.text = s;
    u.message.chat = new Chat();
    u.message.chat.id = -1;
    return new HttpServletRequestWrapper()
  }

  private static class TelegramServletMock extends TelegramServlet
  {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
      super.doPost(req, resp);
    }
  }
}
