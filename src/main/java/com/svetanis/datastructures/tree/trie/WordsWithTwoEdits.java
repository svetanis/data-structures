package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.List;

// 2452. Words Within Two Edits of Dictionary

public final class WordsWithTwoEdits {
  // Time Complexity: O(n * m * l)
  // Space Complexity: O(m)

  public static List<String> twoEditWords(String[] queries, String[] dictionary) {
    List<String> list = new ArrayList<>();
    int len = queries[0].length();
    for (String query : queries) {
      for (String word : dictionary) {
        int diffs = 0;
        for (int i = 0; i < len; i++) {
          if (query.charAt(i) != word.charAt(i)) {
            diffs += 1;
          }
        }

        if (diffs < 3) {
          list.add(query);
          break;
        }
      }
    }
    return list;
  }

  public static void main(String[] args) {
    String[] q1 = { "word", "note", "ants", "wood" };
    String[] d1 = { "wood", "joke", "moat" };
    System.out.println(twoEditWords(q1, d1)); // word, note, wood

    String[] q2 = { "yes" };
    String[] d2 = { "not" };
    System.out.println(twoEditWords(q2, d2)); // []
  }
}
