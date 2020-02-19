package com.github.dagwud.woodlands.game.domain;

public class Tuple<O, T>
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
