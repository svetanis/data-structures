package com.svetanis.datastructures.graph.directed.eulerian;

import java.util.ArrayList;
import java.util.Collections;
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
// The one idea: read each pair as an arrow start -> end; an arrangement that uses every pair once
// is a walk that crosses every arrow exactly once (an Euler path). Where it starts comes from one
// count per number, balance = times as a source - times as a destination: every time the chain
// passes through a number it arrives once and leaves once, so only the first number is at +1. If
// no number is, the chain is a loop and may start at any pair's start. The walk is 332's: from a
// number, keep taking one unused pair out of it (remove takes it off the number's list, so it is
// used once) and follow it; when a number has no unused pair left, write it down. A number is
// written when it is stuck, so the list is the chain backwards -- hence the reverse -- and pair i
// of the answer is the two neighbouring numbers trip[i], trip[i + 1].
//
// Time: O(V + E) -- each pair is taken once and each number written once; V numbers are looked
// up in hash maps.
// Space: O(V + E) -- the maps and the lists, plus the recursion, which goes one level deeper for
// every pair followed before the walk gets stuck.
// Known limit: a chain of about ten thousand pairs overflows the default thread stack, although
// LeetCode allows 10^5.
//
// Siblings: ValidArrangementOfPairIterative is the same walk with a stack of numbers, for long
// chains.

public final class ValidArrangementOfPair {

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
    visit(start, graph, trip); // WALK
    Collections.reverse(trip); // REVERSE: the numbers were written from the end of the chain
    int[][] result = new int[pairs.length][];
    for (int i = 1; i < trip.size(); i++) {
      result[i - 1] = new int[] { trip.get(i - 1), trip.get(i) }; // PAIRS: neighbours on the walk
    }
    return result;
  }

  private void visit(int number, Map<Integer, List<Integer>> graph, List<Integer> trip) {
    List<Integer> list = graph.get(number); // null: no pair starts at this number
    while (list != null && !list.isEmpty()) {
      // TAKE: one pair, removed from the list -- that removal is the mark; then follow it
      visit(list.remove(list.size() - 1), graph, trip);
    }
    trip.add(number); // WRITE: no unused pair left at this number
  }

  public static void main(String[] agrs) {
    int[][] edges1 = { { 5, 1 }, { 4, 5 }, { 11, 9 }, { 9, 4 } };
    ValidArrangementOfPair cst1 = new ValidArrangementOfPair();
    print(cst1.validArrangement(edges1)); // [11,9],[9,4],[4,5],[5,1]

    int[][] edges2 = { { 1, 3 }, { 3, 2 }, { 2, 1 } };
    ValidArrangementOfPair cst2 = new ValidArrangementOfPair();
    print(cst2.validArrangement(edges2)); // [1,3],[3,2],[2,1]

    int[][] edges3 = { { 1, 2 }, { 1, 3 }, { 2, 1 } };
    ValidArrangementOfPair cst3 = new ValidArrangementOfPair();
    print(cst3.validArrangement(edges3)); // [1,2],[2,1],[1,3]
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
