package com.github.dagwud.woodlands.game.domain;

import java.io.Serializable;

public class Tuple<O extends Serializable, T extends Serializable> implements Serializable
{
  private O one;
  private T two;

  public Tuple(O one, T two)
  {
    this.one = one;
    this.two = two;
  }

  public O getOne()
  {
    return one;
  }

  public T getTwo()
  {
    return two;
  }
}
