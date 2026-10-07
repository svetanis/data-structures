package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 649. Dota2 Senate
//
// senate has one letter per senator, in voting order: 'R' for Radiant, 'D' for Dire.
// Returns the party that wins, "Radiant" or "Dire".
//
// One queue per party holds its senators' positions, in voting order. The earlier of the two
// front senators votes first and bans the other one: the opponent due to vote soonest. The voter
// goes to the back of its own queue as position + n, which puts it after every senator still
// waiting in this round. Each meeting removes one senator, so a queue empties within n meetings.

public final class Dota2Senate649 {
  // Time Complexity: O(n), each meeting removes one senator for good
  // Space Complexity: O(n) for the two queues

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
      if (ri < di) { // the earlier position votes first and bans the other
        rq.offer(ri + n); // votes again next round, behind everyone left in this one
      } else {
        dq.offer(di + n);
      }
      rq.poll(); // both leave the front: the banned one for good, the voter was re-added
      dq.poll();
    }
    return rq.isEmpty() ? "Dire" : "Radiant";
  }

  public static void main(String[] args) {
    System.out.println(game("RD")); // Radiant
    System.out.println(game("RDD")); // Dire
  }
}