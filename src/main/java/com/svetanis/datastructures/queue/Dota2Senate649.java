package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 649. Dota2 Senate
//
// Input: senate -- one letter per senator, in voting order: 'R' for Radiant, 'D' for Dire. Voting
// goes round the line again and again. On his turn a senator bans one opponent, who loses the
// vote for good, or, when every senator left is on his own side, announces that his party has
// won. Returns the winning party, "Radiant" or "Dire". On LeetCode: n == senate.length,
// 1 <= n <= 10^4, senate[i] is 'R' or 'D'.
//
// The one idea: one queue entry is one senator still in the game, stored as the turn on which
// that senator next votes: the position in the line, plus n for every full round already spent.
// Each party keeps its senators in a queue in turn order, so the front of a queue is that party's
// next voter. Compare the two fronts. The smaller turn votes first, and the best ban is the
// opponent due to vote soonest, which is the front of the other queue. The voter goes to the
// back of its own queue as turn + n, behind every senator still waiting in this round; the
// banned senator is not put back. Each meeting removes one senator, so one queue empties
// first, and the other party wins.
//
// Time: O(n) -- each meeting removes one senator for good, so there are at most n meetings,
//   each with constant queue work
// Space: O(n) -- the two queues together hold at most n senators

public final class Dota2Senate649 {
  public static String game(String senate) {
    int n = senate.length();
    Deque<Integer> rq = new ArrayDeque<>();
    Deque<Integer> dq = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
      char curr = senate.charAt(i);
      if (curr == 'R') {
        rq.offer(i); // SPLIT: Radiant turns, in line order
      } else {
        dq.offer(i); // SPLIT: Dire turns, in line order
      }
    }

    while (!rq.isEmpty() && !dq.isEmpty()) { // MEET: the two next voters, until one party is gone
      int ri = rq.peek();
      int di = dq.peek();
      if (ri < di) { // COMPARE: the earlier turn votes first and bans the other
        rq.offer(ri + n); // REQUEUE: votes again next round, behind everyone left in this one
      } else {
        dq.offer(di + n);
      }
      rq.poll(); // REMOVE: both leave the front: the banned one for good, the voter was re-added
      dq.poll();
    }
    return rq.isEmpty() ? "Dire" : "Radiant"; // RETURN: the party whose queue is not empty
  }

  public static void main(String[] args) {
    System.out.println(game("RD")); // Radiant
    System.out.println(game("RDD")); // Dire
    System.out.println(game("DDRRR")); // Dire
  }
}