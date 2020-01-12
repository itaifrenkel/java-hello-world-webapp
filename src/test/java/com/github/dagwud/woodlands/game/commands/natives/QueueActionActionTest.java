package com.github.dagwud.woodlands.game.commands.natives;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueActionActionTest
{
  @Test
  public void testMeasurementMilliseconds()
  {
    String delay = "5ms";
    assertEquals(5L, new QueueActionAction().determineDurationMS(delay));
  }

  @Test
  public void testMeasurementSeconds()
  {
    String delay = "5s";
    assertEquals(1000L * 5, new QueueActionAction().determineDurationMS(delay));
  }

  @Test
  public void testMeasurementMinutes()
  {
    String delay = "5m";
    assertEquals(1000L * 60 * 5, new QueueActionAction().determineDurationMS(delay));
  }

  @Test
  public void testMeasurementHours()
  {
    String delay = "5h";
    assertEquals(1000L * 60 * 60 * 5, new QueueActionAction().determineDurationMS(delay));
  }
}