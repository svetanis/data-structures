package com.svetanis.datastructures.graph.directed.ts.alien;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 269. Alien Dictionary
//
// Input: words -- a list of words from an alien language, sorted in that language's alphabetical
// order, over lower-case letters. Returns: a string of every letter that appears in the words, in
// an order that makes the list sorted; any such order will do. Returns "" if no order can make the
// list sorted (the words contradict each other, or a word comes after a longer word it is a prefix
// of). On LeetCode: 1 <= words.length <= 100, 1 <= words[i].length <= 100.
//
// The one idea: two neighbouring words differ first at some position, and there the letter of the
// earlier word comes before the letter of the later word. Each such pair is an edge "x before y",
// and an order that puts every x before its y is a topological order of those edges, found by
// Kahn's method: letters with nothing pointing at them go first, and placing a letter frees the
// letters it pointed at. If the line does not take in every letter, the edges contain a cycle.
// Here a letter is its own array index, c - 'a', so the graph is 26 lists and the in-degrees are
// 26 numbers.
//
// A pair of neighbours with no differing position, the first longer than the second, gives no edge
// at all and is the other way an order can be impossible; it is read off the absence of a
// differing position.
//
// Time: O(C) -- C is the total number of letters in the words; at most 26 letters are placed and
// there is at most one edge per neighbouring pair.
// Space: O(1) beyond the edges -- arrays of 26, and at most one edge per neighbouring pair.
//
// Siblings: AlienOrderSubmit is the same algorithm over Map<Character, ...>; it tests
// w1.startsWith(w2) before comparing, where this one searches for the first differing position and
// reads the failure off its absence. AlienDictionary does the same with static helpers; AlienOrder
// returns the smallest order.

public final class AlienOrderArrays {

  private static final int ALPHABET = 26;

  private static final String NO_ORDER = "";

  public static String alienOrder(String[] words) {
    List<Integer>[] graph = graph();
    int[] inDegree = new int[ALPHABET];
    boolean[] present = present(words);

    for (int i = 1; i < words.length; i++) {
      String first = words[i - 1];
      String second = words[i];
      int index = firstDifferent(first, second);
      if (index == -1) {
        // one word is a prefix of the other: no letters conflict, only the lengths
        if (first.length() > second.length()) { // REJECT: longer word before its own prefix
          return NO_ORDER;
        }
        continue;
      }
      int from = first.charAt(index) - 'a';
      int to = second.charAt(index) - 'a';
      graph[from].add(to); // EDGE: from comes before to
      inDegree[to]++; // COUNT the edge at its head
    }

    StringBuilder sb = new StringBuilder();
    Deque<Integer> queue = sources(present, inDegree);
    while (!queue.isEmpty()) {
      int letter = queue.poll();
      sb.append((char) (letter + 'a')); // PLACE letter next
      for (int next : graph[letter]) {
        if (--inDegree[next] == 0) { // USE UP the edge letter -> next
          queue.offer(next);
        }
      }
    }
    return sb.length() == count(present) ? sb.toString() : NO_ORDER; // RETURN: short = a cycle
  }

  @SuppressWarnings("unchecked")
  private static List<Integer>[] graph() {
    List<Integer>[] graph = new ArrayList[ALPHABET];
    for (int letter = 0; letter < ALPHABET; letter++) {
      graph[letter] = new ArrayList<>();
    }
    return graph;
  }

  // every letter appearing anywhere is a node, including one no edge ever mentions
  private static boolean[] present(String[] words) {
    boolean[] present = new boolean[ALPHABET];
    for (String word : words) {
      for (char c : word.toCharArray()) {
        present[c - 'a'] = true;
      }
    }
    return present;
  }

  // both halves: the 21 letters that never appear also sit at in-degree 0
  private static Deque<Integer> sources(boolean[] present, int[] inDegree) {
    Deque<Integer> queue = new ArrayDeque<>();
    for (int letter = 0; letter < ALPHABET; letter++) {
      if (present[letter] && inDegree[letter] == 0) { // SOURCE: appears, and nothing has to come before it
        queue.offer(letter);
      }
    }
    return queue;
  }

  private static int count(boolean[] present) {
    int total = 0;
    for (boolean appears : present) {
      if (appears) {
        total++;
      }
    }
    return total;
  }

  // the first position where the two words differ, or -1 if one is a prefix of the other
  private static int firstDifferent(String first, String second) {
    int shorter = Math.min(first.length(), second.length());
    for (int i = 0; i < shorter; i++) {
      if (first.charAt(i) != second.charAt(i)) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    print(new String[] {"wrt", "wrf", "er", "ett", "rftt"});   // wertf
    print(new String[] {"z", "x"});                            // zx
    print(new String[] {"z", "x", "z"});                       // "" -- a cycle
    print(new String[] {"abc", "ab"});                         // "" -- impossible, and NOT a cycle
    print(new String[] {"ab", "abc"});                         // abc -- valid, no edges at all
    print(new String[] {"z", "z"});                            // z
    print(new String[] {"wrt", "wrf"});                        // rtwf -- w and r unconstrained
  }

  private static void print(String[] words) {
    System.out.println(String.join(",", words) + " -> " + alienOrder(words));
  }
}
