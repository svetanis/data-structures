package com.svetanis.datastructures.tree.trie.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 642. Design Search Autocomplete System
//
// Input: the constructor gets sentences and times, where sentences[i] was typed times[i] times.
// input(c) receives the typed characters one at a time and returns the three most often typed
// sentences that start with everything typed so far, hottest first and, on equal counts, in
// ASCII order (a space sorts before a letter). The character '#' ends the sentence: it is saved
// with one more count, the typed text starts over, and the answer is an empty list. On
// LeetCode: 1 <= sentences.length <= 100, 1 <= sentences[i].length <= 100, 1 <= times[i] <= 50,
// sentences hold lowercase letters and spaces, c is a lowercase letter, ' ' or '#', at most
// 5000 calls.
//
// The one idea: one Node of the trie is one typed prefix, and it holds top, the three hottest
// sentences that start with that prefix. A keystroke is then one step down from where the
// cursor stands and a read of that node's top. The cursor is the node the typed text has reached.
//
// Every node keeps its own top 3, so a keystroke is one step down plus a read.
// AutocompleteSystem instead walks from the root and searches the whole subtree
// on every keystroke.
//
// Why the lists stay right: a sentence's count only ever goes up, and only when
// that sentence is saved. So the only lists that can change are the ones on its
// own path, and a sentence dropped from a list can only come back by being saved
// again -- which updates that same path.
//
// Time: O(1) per keystroke that is not '#' (one step and a copy of three entries); a save costs
// O(L^2) -- L nodes on the sentence's path, each sorting at most four sentences whose
// comparison reads up to L characters
// Space: O(total characters * 27) for the trie, plus at most three sentences listed on each node
//
// Siblings: AutocompleteSystem (same problem, subtree search with a heap on every keystroke).

public final class AutocompleteSystemTopThree {

  private final Node root = new Node();
  private final Map<String, Integer> counts = new HashMap<>();
  private final Comparator<String> hottestFirst = this::compareHotness;
  private final StringBuilder typed = new StringBuilder();
  private Node cursor = root; // null once the typed prefix has left the trie

  public AutocompleteSystemTopThree(String[] sentences, int[] times) {
    for (int i = 0; i < sentences.length; i++) {
      save(sentences[i], times[i]); // BUILD: each sentence with its count
    }
  }

  public List<String> input(char c) {
    if (c == '#') {
      save(typed.toString(), 1); // SAVE the typed sentence, one more time
      typed.setLength(0); // RESET the typed text and the cursor
      cursor = root;
      return new ArrayList<>();
    }
    typed.append(c);
    if (cursor != null) {
      cursor = cursor.children[index(c)]; // one step, never from the root
    }
    return cursor == null ? new ArrayList<>() : new ArrayList<>(cursor.top); // RETURN: read the node's top
  }

  private void save(String sentence, int times) {
    counts.merge(sentence, times, Integer::sum); // COUNT: the sentence's new total
    Node node = root;
    for (char c : sentence.toCharArray()) {
      int index = index(c);
      if (node.children[index] == null) {
        node.children[index] = new Node(); // CREATE the missing letter
      }
      node = node.children[index];
      node.update(sentence, hottestFirst); // every node of the path
    }
  }

  private int compareHotness(String a, String b) {
    int byCount = Integer.compare(counts.get(b), counts.get(a)); // MORE typed first
    return byCount != 0 ? byCount : a.compareTo(b); // TIE: ASCII order
  }

  private static int index(char c) {
    return c == ' ' ? 26 : c - 'a'; // slot 26 is the space
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
      top.remove(sentence); // REMOVE the old entry, if the sentence was already listed
      top.add(sentence);
      top.sort(hottestFirst); // SORT: hottest first
      if (top.size() > 3) {
        top.remove(3); // DROP the fourth
      }
    }
  }
}
