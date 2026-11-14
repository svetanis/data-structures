package com.svetanis.datastructures.tree.trie.autocomplete;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// 642. Design Search Autocomplete System
//
// Input: the constructor gets sentences and times, where sentences[i] was typed times[i] times.
// autocomplete(c) receives the typed characters one at a time and returns the three most often
// typed sentences that start with everything typed so far, hottest first and, on equal counts,
// in ASCII order (a space sorts before a letter). The character '#' ends the sentence: it is
// saved with one more count, the typed text starts over, and the answer is an empty list. On
// LeetCode: 1 <= sentences.length <= 100, 1 <= sentences[i].length <= 100, 1 <= times[i] <= 50,
// sentences hold lowercase letters and spaces, c is a lowercase letter, ' ' or '#', at most
// 5000 calls to autocomplete.
//
// The one idea: a trie of the sentences in which the node a sentence ends on holds that sentence
// and its count (freq). The typed text is a path from the root; every sentence that starts
// with it is on a node somewhere below the node the path ends on. Each keystroke walks the typed
// text from the root, searches all of the subtree below it, and keeps the best three in a heap
// of size three whose top is the worst of the three, so the one that drops out is the worst.
//
// Time: O(P + M) per keystroke -- P typed characters walked, M trie nodes below the prefix; the
// heap holds three entries so each offer is O(1)
// Space: O(total characters * 27) for the trie, plus the typed text and the heap
//
// Siblings: AutocompleteSystemTopThree keeps a top 3 on each node instead, so a keystroke is one
// step down plus a read; this file searches the whole subtree on every keystroke.

public final class AutocompleteSystem {

  private Trie root = new Trie();
  private StringBuilder sb = new StringBuilder();

  public AutocompleteSystem(String[] sentences, int[] times) {
    for (int i = 0; i < sentences.length; i++) {
      root.insert(sentences[i], times[i]); // BUILD: each sentence with its count
    }
  }

  public List<String> autocomplete(char inputChar) {
    List<String> list = new ArrayList<>();
    if (inputChar == '#') {
      root.insert(sb.toString(), 1); // SAVE the typed sentence, one more time
      sb = new StringBuilder(); // RESET the typed text
      return list;
    }
    sb.append(inputChar);
    Trie node = root.search(sb.toString()); // WALK the typed text from the root
    if (node == null) {
      return list;
    }
    // WORST on top: lower count first, and on equal counts the later sentence in ASCII order first
    PriorityQueue<Trie> pq = new PriorityQueue<>(
        comparingInt((Trie t) -> t.freq).thenComparing(t -> t.word, reverseOrder()));
    dfs(node, pq);
    while (!pq.isEmpty()) {
      list.add(0, pq.poll().word); // the worst comes out first, so each one goes in front
    }
    return list;
  }

  private void dfs(Trie node, PriorityQueue<Trie> pq) {
    if (node == null) {
      return;
    }
    if (node.freq > 0) {
      pq.offer(node); // OFFER: a sentence ends on this node
      if (pq.size() > 3) {
        pq.poll(); // DROP the worst, so only the best three stay
      }
    }
    for (Trie child : node.children) {
      dfs(child, pq);
    }
  }

  public static void main(String[] args) {
    int[] times = { 5, 3, 2, 1 };
    String[] words = { "i love you", "island", "i love leetcode", "ironman" };
    AutocompleteSystem acs = new AutocompleteSystem(words, times);
    System.out.println(acs.autocomplete('i')); // [i love you, island, i love leetcode]
    System.out.println(acs.autocomplete(' ')); // [i love you, i love leetcode]
    System.out.println(acs.autocomplete('a')); // []
    System.out.println(acs.autocomplete('#')); // []
  }

  public static final class Trie {

    private int freq;
    private String word;
    private Trie[] children;

    public Trie() {
      this.freq = 0;
      this.word = "";
      this.children = new Trie[27];
    }

    public void insert(String s, int frequency) {
      Trie node = this;
      for (char c : s.toCharArray()) {
        int index = c == ' ' ? 26 : c - 'a'; // slot 26 is the space
        if (node.children[index] == null) {
          node.children[index] = new Trie(); // CREATE the missing letter
        }
        node = node.children[index];
      }
      node.freq += frequency; // COUNT: the sentence ends here
      node.word = s;
    }

    public Trie search(String prefix) {
      Trie node = this;
      for (char c : prefix.toCharArray()) {
        int index = c == ' ' ? 26 : c - 'a';
        if (node.children[index] == null) {
          return null; // MISS: no sentence starts with this
        }
        node = node.children[index]; // WALK one letter down
      }
      return node;
    }
  }
}
