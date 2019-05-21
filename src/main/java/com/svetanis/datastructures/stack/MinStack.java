package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class MinStack extends Stack<Pair<Integer, Integer>> {

  private static final long serialVersionUID = 1L;

  public void push(int value) {
    int min = Math.min(value, min());
    super.push(Pair.build(value, min));
  }

  public int min() {
    if (isEmpty()) {
      throw illegalState("stack underflow");
    } else {
      return peek().getRight();
    }
  }

}
