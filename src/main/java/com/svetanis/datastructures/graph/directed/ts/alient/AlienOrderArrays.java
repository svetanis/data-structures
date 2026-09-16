package com.svetanis.datastructures.graph.directed.ts.alient;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// LC 269 - Alien Dictionary
//
// same algorithm as AlienOrderSubmit, over int[26] arrays instead of a Map<Character, ...>:
// a letter is its own index, c - 'a', so Kahn's loop is the plain array-indexed one
//
// the other difference is how the impossible input is spotted. AlienOrderSubmit tests
// w1.startsWith(w2) before comparing; this one searches for the first differing position
// and reads the failure off its absence -- no differing position, and the first word longer

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
        if (first.length() > second.length()) {
          return NO_ORDER;
        }
        continue;
      }
      int from = first.charAt(index) - 'a';
      int to = second.charAt(index) - 'a';
      graph[from].add(to);
      inDegree[to]++;
    }

    StringBuilder sb = new StringBuilder();
    Deque<Integer> queue = sources(present, inDegree);
    while (!queue.isEmpty()) {
      int letter = queue.poll();
      sb.append((char) (letter + 'a'));
      for (int next : graph[letter]) {
        if (--inDegree[next] == 0) {
          queue.offer(next);
        }
      }
    }
    return sb.length() == count(present) ? sb.toString() : NO_ORDER;
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
      if (present[letter] && inDegree[letter] == 0) {
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
