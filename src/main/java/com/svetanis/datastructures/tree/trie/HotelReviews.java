package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Hotel Reviews (not a LeetCode problem)
//
// Input: good -- the good words joined with '_'; list -- the reviews, each one its words joined
// with '_'. Returns the positions of the reviews ordered by how many of their words are good words,
// most first; a word that appears twice in a review counts twice, and reviews with the same score
// keep their original order.
//
// The one idea: put the good words in a trie, so that "is this word a good word" is one walk down
// the letters of the word. One Rank holds a review's position and its score. Score every review by
// asking the trie about each of its words, then sort the Ranks: higher score first, lower position
// first when the scores tie.
//
// Time: O(G + R + n log n) -- G letters to build the trie, R letters in all the reviews to look the
// words up, and n log n to sort the n ranks
// Space: O(G + n) -- the trie, and one Rank per review
//
// Siblings: Trie.java is the trie this file builds; no other file solves this problem.

public final class HotelReviews {

  public static List<Integer> sortReviews(String good, List<String> list) {
    Node root = Trie.build(words(good)).getRoot(); // BUILD: the good words, one walk each
    List<Rank> ranks = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      int score = 0;
      for (String str : words(list.get(i))) {
        if (search(root, str)) {
          score++; // SCORE: this word of review i is a good word
        }
      }
      ranks.add(new Rank(i, score));
    }
    Collections.sort(ranks); // SORT: higher score first, lower position first on a tie
    List<Integer> indexes = new ArrayList<>();
    for (Rank rank : ranks) {
      indexes.add(rank.index); // RETURN: only the positions
    }
    return indexes;
  }

  // the words between the '_' characters; empty pieces are not words
  private static List<String> words(String str) {
    List<String> list = new ArrayList<>();
    for (String word : str.split("_")) {
      if (!word.isEmpty()) {
        list.add(word); // KEEP: a real word
      }
    }
    return list;
  }

  private static boolean search(Node root, String str) {
    Node node = root;
    for (char c : str.toCharArray()) {
      if (!node.children.containsKey(c)) {
        return false; // MISS: no good word has this letter next
      }
      node = node.children.get(c); // WALK one letter down
    }
    return node != null && node.endOfWord; // RETURN: the walk ended on a whole good word, not on a prefix of one
  }

  public static void main(String[] args) {
    String good = "cool_ice_wifi";
    List<String> list = Arrays.asList("water_is_cool", "cold_ice_drink", "cool_wifi_speed");
    System.out.println(sortReviews(good, list)); // [2, 0, 1]
  }

  private static class Rank implements Comparable<Rank> {
    int index;
    int score;

    public Rank(int index, int score) {
      this.index = index;
      this.score = score;
    }

    @Override
    public int compareTo(Rank other) {
      if (this.score == other.score) {
        return Integer.compare(this.index, other.index); // TIE: the earlier review first
      }
      return Integer.compare(other.score, this.score); // MORE good words first
    }

    @Override
    public String toString() {
      return index + ":" + score;
    }
  }
}
