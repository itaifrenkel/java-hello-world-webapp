package com.github.dagwud.woodlands.game.commands.invocation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueResolverTest
{
  private VariableStack vars;

  @Before
  public void setup()
  {
    vars = new VariableStack();
    Variables values = new Variables();
    values.put("single", "one");
    values.put("double", "two");
    values.put("triple", "three");
    values.put("quadruple", "four");
    vars.pushNewVariablesStackFrame("test", values);
  }

  @Test
  public void testResolveStringLiteral() throws VariableUndefinedException
  {
    String expr = "test value";
    String resolved = ValueResolver.resolve(expr, vars);
    assertEquals("test value", resolved);
  }

  @Test
  public void testResolveSimpleVariable() throws VariableUndefinedException
  {
    String expr = "${single}";
    assertEquals("one", ValueResolver.resolve(expr, vars));
  }

  @Test
  public void testResolveMultipleVariables()
  {
    String expr = "${single}${triple}";
    assertEquals("onethree", ValueResolver.resolve(expr, vars));
  }

  @Test
  public void testResolveMultipleVariablesWithTextBetween()
  {
    String expr = "*${single}-${triple}*";
    assertEquals("*one-three*", ValueResolver.resolve(expr, vars));
  }
}