package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// Tower of Hanoi: moves n disks from peg 0 to peg 1, using peg 2 as the spare.
//
// Input: the number of disks n, all starting on peg 0. Each peg is a stack of disk sizes, disk 1
// the smallest, and only a top disk can move. Nothing is returned; every move is printed.
//
// To move n disks from 'from' to 'to': move the top n - 1 disks to the spare peg, move the
// largest of the n, then move the n - 1 disks from the spare peg onto it. When the largest
// disk moves, the smaller ones are all on the spare peg, so it lands on an empty peg or on a
// larger disk.

public final class TowerOfHanoiRecursive {
  // Time Complexity: O(2^n), moving n disks takes twice the moves for n - 1, plus one
  // Space Complexity: O(n), the n disks on the pegs and n levels of recursion

  private static final int N = 3; // the number of pegs, not disks

  public static void moveTower(int n) {
    Deque<Integer>[] pegs = init(n);
    transfer(n, pegs, 0, 1, 2);
  }

  private static Deque<Integer>[] init(int n) {
    @SuppressWarnings("unchecked")
    Deque<Integer>[] pegs = (Deque<Integer>[]) new Deque[N];

    for (int i = 0; i < N; ++i) {
      pegs[i] = new ArrayDeque<>();
    }

    for (int i = n; i >= 1; --i) {
      pegs[0].push(i);
    }
    return pegs;
  }

  private static void transfer(int n, Deque<Integer>[] pegs, int from, int to, int aux) {
    
    if (n <= 0) {
      return;
    }

    transfer(n - 1, pegs, from, aux, to); // clear the smaller disks onto the spare peg

    pegs[to].push(pegs[from].pop()); // the largest of the n disks
    System.out.println("Move from peg " + from + " to peg " + to);

    transfer(n - 1, pegs, aux, to, from);
  }

  public static void main(String[] args) {
    moveTower(3);
  }
}
