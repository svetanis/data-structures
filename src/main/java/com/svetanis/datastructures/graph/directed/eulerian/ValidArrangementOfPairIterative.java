package com.svetanis.datastructures.graph.directed.eulerian;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2097. Valid Arrangement of Pairs
//
// Input: pairs, where pairs[i] = {start, end}. An arrangement is a reordering of the pairs in
// which every pair starts where the one before it ended: end of pair i - 1 equals start of
// pair i. Returns any valid arrangement. On LeetCode: 1 <= pairs.length <= 10^5,
// pairs[i].length == 2, 0 <= start, end <= 10^9, start != end, no two pairs are equal, and a
// valid arrangement is guaranteed to exist.
//
// The one idea: the same Euler path and the same start as ValidArrangementOfPair, with a stack of
// numbers in place of the recursion. One stack entry is an open call: a number the walk has reached
// and not yet written down; the top is where the walk is now, and it is only looked at (peek), not
// taken off, while it still has pairs. If the top still has an unused pair, take one off its list
// and push the number it leads to -- that is the recursive call. If the top has none left, pop it
// and write it down -- that is the recursive call returning. The numbers are written in exactly
// the order the recursion writes them, so the list is the chain backwards, and the reverse at the
// end puts it right.
//
// Time: O(V + E) -- each pair is taken once and each number written once; V numbers are looked
// up in hash maps.
// Space: O(V + E) -- the maps, the lists and the stack, which holds at most E + 1 numbers.
//
// Siblings: ValidArrangementOfPair is the recursive walk, shorter to write but it overflows a
// default Java stack on a very long chain of pairs.

public final class ValidArrangementOfPairIterative {

  public int[][] validArrangement(int[][] pairs) {
    // BUILD: number -> the numbers its pairs go to; balance = as source - as destination
    Map<Integer, List<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> balance = new HashMap<>();
    for (int[] pair : pairs) {
      graph.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]);
      balance.merge(pair[0], 1, Integer::sum);
      balance.merge(pair[1], -1, Integer::sum);
    }
    int start = pairs[0][0]; // START: all balanced -- a loop, start at any pair's start
    for (int key : balance.keySet()) {
      if (balance.get(key) == 1) {
        start = key; // START: one more pair out than in
      }
    }
    List<Integer> trip = new ArrayList<>(); // ArrayList: get(i) below is one step
    Deque<Integer> stack = new ArrayDeque<>(); // OPEN: numbers reached and not yet written
    stack.push(start);
    while (!stack.isEmpty()) {
      List<Integer> list = graph.get(stack.peek()); // PEEK: the top stays while it has pairs
      if (list == null || list.isEmpty()) {
        trip.add(stack.pop()); // WRITE: no unused pair left at this number
      } else {
        // TAKE: one pair, removed from the list -- that removal is the mark; stand at its end
        stack.push(list.remove(list.size() - 1));
      }
    }
    Collections.reverse(trip); // REVERSE: the numbers were written from the end of the chain
    int[][] result = new int[pairs.length][];
    for (int i = 1; i < trip.size(); i++) {
      result[i - 1] = new int[] { trip.get(i - 1), trip.get(i) }; // PAIRS: neighbours on the walk
    }
    return result;
  }

  public static void main(String[] args) {
    int[][] edges1 = { { 5, 1 }, { 4, 5 }, { 11, 9 }, { 9, 4 } };
    // [11,9],[9,4],[4,5],[5,1]
    print(new ValidArrangementOfPairIterative().validArrangement(edges1));

    int[][] edges2 = { { 1, 3 }, { 3, 2 }, { 2, 1 } };
    print(new ValidArrangementOfPairIterative().validArrangement(edges2)); // [1,3],[3,2],[2,1]

    int[][] edges3 = { { 1, 2 }, { 1, 3 }, { 2, 1 } };
    print(new ValidArrangementOfPairIterative().validArrangement(edges3)); // [1,2],[2,1],[1,3]
  }

  private static void print(int[][] matrix) {
    for (int[] row : matrix) {
      for (int x : row) {
        System.out.print(x);
        System.out.print(' ');
      }
      System.out.println();
    }
    System.out.println();
  }
}
