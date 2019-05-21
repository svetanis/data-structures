package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

public final class MinStackAux extends Stack<Integer> {

  private static final long serialVersionUID = 1L;

  private Stack<Integer> aux;

  public MinStackAux() {
    this.aux = new Stack<>();
  }

  public void push(int value) {
    if (value <= min()) {
      aux.push(value);
    }
    super.push(value);
  }

  @Override
  public Integer pop() {
    int value = super.pop();
    if (value == min()) {
      aux.pop();
    }
    return value;
  }

  public int min() {
    if (aux.empty()) {
      throw illegalState("stack underflow");
    } else {
      return aux.peek();
    }
  }
}