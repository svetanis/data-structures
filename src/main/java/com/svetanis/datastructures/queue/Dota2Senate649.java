package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 649. Dota2 Senate

public final class Dota2Senate649 {
  // Time complexity: O(n)
  // Space complexity: O(n)

  public static String game(String senate) {
    int n = senate.length();
    Deque<Integer> rq = new ArrayDeque<>();
    Deque<Integer> dq = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
      char curr = senate.charAt(i);
      if (curr == 'R') {
        rq.offer(i);
      } else {
        dq.offer(i);
      }
    }

    while (!rq.isEmpty() && !dq.isEmpty()) {
      int ri = rq.peek();
      int di = dq.peek();
      if (ri < di) {
        rq.offer(ri + n);
      } else {
        dq.offer(di + n);
      }
      rq.poll();
      dq.poll();
    }
    return rq.isEmpty() ? "Dire" : "Radiant";
  }

  public static void main(String[] args) {
    System.out.println(game("RD")); // Radiant
    System.out.println(game("RDD")); // Dire
  }
}