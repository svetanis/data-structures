package com.svetanis.datastructures.stack;

import java.util.Stack;

public final class TowerOfHanoiRecursive {

  private static final int N = 3;

  public static void moveTower(int n) {
    Stack<Integer>[] pegs = init(n);
    transfer(n, pegs, 0, 1, 2);
  }

  private static Stack<Integer>[] init(int n) {
    @SuppressWarnings("unchecked")
    Stack<Integer>[] pegs = (Stack<Integer>[]) new Stack[N];

    for (int i = 0; i < N; ++i) {
      pegs[i] = new Stack<Integer>();
    }

    for (int i = n; i >= 1; --i) {
      pegs[0].push(i);
    }
    return pegs;
  }

  private static void transfer(int n, Stack<Integer>[] pegs, int from, int to, int aux) {
    
    if (n <= 0) {
      return;
    }

    transfer(n - 1, pegs, from, aux, to);

    pegs[to].push(pegs[from].pop());
    System.out.println("Move from peg " + from + " to peg " + to);

    transfer(n - 1, pegs, aux, to, from);
  }

  public static void main(String[] args) {
    moveTower(3);
  }
}
