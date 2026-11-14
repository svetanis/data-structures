package com.svetanis.datastructures.tree.trie.twoedits;

import java.util.ArrayList;
import java.util.List;

// 2452. Words Within Two Edits of Dictionary
//
// Input: two arrays of words, queries and dictionary, all of the same length. One edit changes one
// letter of a word to any other letter. Returns the queries that can be turned into some
// dictionary word with at most two edits, in their original order. On LeetCode:
// 1 <= queries.length, dictionary.length <= 100, 1 <= queries[i].length == dictionary[j].length
// <= 100, lowercase letters.
//
// The one idea: because every word has the same length, turning a query into a dictionary word
// takes exactly as many edits as the two words have positions with different letters. So count
// the differing positions of the query against each dictionary word, and keep the query as soon
// as one word differs in 2 or fewer positions.
//
// Time: O(q * d * l) -- q queries, d dictionary words, l letters compared one by one for each pair
// Space: O(1) besides the answer, which holds at most q queries
//
// Siblings: WordsWithTwoEditsTrie walks a trie of the dictionary and spends the two edits on the
// way down; this file compares every query with every dictionary word.

public final class WordsWithTwoEditsBruteForce {

  public static List<String> twoEditWords(String[] queries, String[] dictionary) {
    List<String> list = new ArrayList<>();
    int len = queries[0].length();
    for (String query : queries) {
      for (String word : dictionary) {
        int diffs = 0;
        for (int i = 0; i < len; i++) {
          if (query.charAt(i) != word.charAt(i)) {
            diffs += 1; // COUNT: one more position that needs an edit
          }
        }

        if (diffs < 3) {
          list.add(query); // TAKE: at most two edits away from this word
          break; // one dictionary word is enough
        }
      }
    }
    return list;
  }

  public static void main(String[] args) {
    String[] q1 = { "word", "note", "ants", "wood" };
    String[] d1 = { "wood", "joke", "moat" };
    System.out.println(twoEditWords(q1, d1)); // [word, note, wood]

    String[] q2 = { "yes" };
    String[] d2 = { "not" };
    System.out.println(twoEditWords(q2, d2)); // []
  }
}
