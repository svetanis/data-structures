package com.svetanis.datastructures.tree.trie.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 642. Design Search Autocomplete System

// Every node keeps its own top 3, so a keystroke is one step down plus a read.
// AutocompleteSystem instead walks from the root and searches the whole subtree
// on every keystroke.
//
// Why the lists stay right: a sentence's count only ever goes up, and only when
// that sentence is saved. So the only lists that can change are the ones on its
// own path, and a sentence dropped from a list can only come back by being saved
// again -- which updates that same path.

public final class AutocompleteSystemTopThree {

  private final Node root = new Node();
  private final Map<String, Integer> counts = new HashMap<>();
  private final Comparator<String> hottestFirst = this::compareHotness;
  private final StringBuilder typed = new StringBuilder();
  private Node cursor = root; // null once the typed prefix has left the trie

  public AutocompleteSystemTopThree(String[] sentences, int[] times) {
    for (int i = 0; i < sentences.length; i++) {
      save(sentences[i], times[i]);
    }
  }

  public List<String> input(char c) {
    if (c == '#') {
      save(typed.toString(), 1);
      typed.setLength(0);
      cursor = root;
      return new ArrayList<>();
    }
    typed.append(c);
    if (cursor != null) {
      cursor = cursor.children[index(c)]; // one step, never from the root
    }
    return cursor == null ? new ArrayList<>() : new ArrayList<>(cursor.top);
  }

  private void save(String sentence, int times) {
    counts.merge(sentence, times, Integer::sum);
    Node node = root;
    for (char c : sentence.toCharArray()) {
      int index = index(c);
      if (node.children[index] == null) {
        node.children[index] = new Node();
      }
      node = node.children[index];
      node.update(sentence, hottestFirst); // every node of the path
    }
  }

  private int compareHotness(String a, String b) {
    int byCount = Integer.compare(counts.get(b), counts.get(a));
    return byCount != 0 ? byCount : a.compareTo(b);
  }

  private static int index(char c) {
    return c == ' ' ? 26 : c - 'a';
  }

  public static void main(String[] args) {
    String[] sentences = { "i love you", "island", "iroman", "i love leetcode" };
    int[] times = { 5, 3, 2, 2 };
    AutocompleteSystemTopThree acs = new AutocompleteSystemTopThree(sentences, times);
    System.out.println(acs.input('i')); // [i love you, island, i love leetcode]
    System.out.println(acs.input(' ')); // [i love you, i love leetcode]
    System.out.println(acs.input('a')); // []
    System.out.println(acs.input('#')); // []
  }

  private static final class Node {
    private final Node[] children = new Node[27];
    private final List<String> top = new ArrayList<>(4); // hottest first, at most 3

    private void update(String sentence, Comparator<String> hottestFirst) {
      top.remove(sentence);
      top.add(sentence);
      top.sort(hottestFirst);
      if (top.size() > 3) {
        top.remove(3);
      }
    }
  }
}
