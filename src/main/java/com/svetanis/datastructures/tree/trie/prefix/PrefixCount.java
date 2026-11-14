package com.svetanis.datastructures.tree.trie.prefix;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// Prefix count.
// Given a dictionary, containing a list of words,
// and a list of queries, which consists of a list of prefixes,
// compute the result of each query. Each query is simply a string
// denoting a prefix. For each query, return the number of words in
// the dictionary that contain that prefix.
//
// There may be duplicate words in the dictionary.
// In a query, you should account for all the duplicate words.
//
// Returns one count per prefix, in query order. The empty prefix counts every word.
//
// The one idea: put every word in a trie where each node also holds a counter: how many
// inserted words pass through it. A node is a prefix (the letters on the path from the
// root), so its counter is the number of words that begin with that prefix -- a duplicate
// word passes through twice and counts twice. Answering a query is then one walk down the
// prefix and one read of the counter where it ends; a missing child means no word begins
// with it, so 0.
//
// Time: O(c) -- c = total letters in the words and the prefixes: one step per letter
// Space: O(c) -- the trie, one node per distinct prefix of a word
//
// Siblings: LeetCode 2185 (Counting Words With a Given Prefix) is this problem for one prefix.
// TrieNode is the counting trie used here; TrieNodeSimple counts the same way with loops.

public final class PrefixCount {

  public static List<Integer> prefixCount(List<String> words, List<String> prefixes) {
    TrieNode trie = build(words);
    return count(trie, prefixes);
  }

  private static List<Integer> count(TrieNode trie, List<String> prefixes) {
    List<Integer> list = new ArrayList<>();
    for (String prefix : prefixes) {
      list.add(trie.prefixQuery(prefix)); // ASK: the counter where the prefix ends
    }
    return list;
  }

  private static TrieNode build(List<String> words) {
    TrieNode trie = new TrieNode();
    for (String word : words) {
      trie.insert(word); // BUILD: every word adds one to each node on its path
    }
    return trie;
  }

  public static void main(String[] args) {
    List<String> words = asList("forgo", "for", "trie", "while", "loop", "stack", "deque", "kruskal", "deck", "decks",
        "logic", "math", "computer", "science", "compute", "scrap", "logos", "loom");
    List<String> prefixes = asList("l", "zeb", "for", "trie", "log", "logi", "sc", "st");
    System.out.println(prefixCount(words, prefixes)); // [4, 0, 2, 1, 2, 1, 2, 1]
  }
}
