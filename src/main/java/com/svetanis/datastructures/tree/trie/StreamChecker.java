package com.svetanis.datastructures.tree.trie;

// 1032. Stream of Characters
//
// Input: the constructor gets the array words; query(letter) then receives the letters of a
// stream one at a time. Returns true when some suffix of the letters received so far, the newest
// letters read back to some point, is one of the words. On LeetCode: 1 <= words.length <= 2000,
// 1 <= words[i].length <= 200, words and letters are lowercase, at most 4 * 10^4 calls to query.
//
// The one idea: a word matches the end of the stream when the stream, read from its newest letter
// backwards, spells the word read backwards. So the trie holds every word reversed: one node is one
// letter of a reversed word, and a node with isEndOfWord set is where a whole word is complete.
// Each query walks the stream from the newest letter backwards down the trie and answers true at
// the first node that completes a word; it stops with false as soon as the next letter has no
// edge, and it never walks further than the longest word.
//
// Time: O(L) per query, L = 200 the longest word -- the walk is cut off by the trie's depth;
// the constructor is O(total letters of the words)
// Space: O(total letters of the words) for the trie, plus O(q) for the q letters of the stream
//
// Siblings: none; the other tries here match from the front of a word, this one from the back.

public final class StreamChecker {

  private TrieNode root;
  private StringBuilder stream;

  public StreamChecker(String[] words) {
    this.root = new TrieNode();
    for (String word : words) {
      this.root.insert(word); // BUILD: each word is stored backwards
    }
    this.stream = new StringBuilder();
  }

  public boolean query(char letter) {
    stream.append(letter); // RECORD the newest letter
    return root.search(stream);
  }

  public static void main(String[] args) {
    String[] w1 = { "cd", "f", "kl" };
    StreamChecker wb1 = new StreamChecker(w1);
    System.out.println(wb1.query('a')); // false
    System.out.println(wb1.query('b')); // false
    System.out.println(wb1.query('c')); // false
    System.out.println(wb1.query('d')); // true
    System.out.println(wb1.query('e')); // false
    System.out.println(wb1.query('f')); // true
    System.out.println(wb1.query('g')); // false
    System.out.println(wb1.query('h')); // false
    System.out.println(wb1.query('i')); // false
    System.out.println(wb1.query('j')); // false
    System.out.println(wb1.query('k')); // false
    System.out.println(wb1.query('l')); // true
  }

  private static class TrieNode {
    private TrieNode[] children = new TrieNode[26];
    private boolean isEndOfWord;

    public void insert(String word) {
      TrieNode node = this;
      for (int i = word.length() - 1; i >= 0; i--) { // BACKWARDS: last letter first
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode(); // CREATE the missing letter
        }
        node = node.children[index];
      }
      node.isEndOfWord = true; // MARK: a whole word is complete here
    }

    public boolean search(StringBuilder stream) {
      TrieNode node = this;
      // the StringBuilder is read in place: length() and charAt() are O(1), no copy of the stream
      for (int i = stream.length() - 1; i >= 0; i--) { // BACKWARDS: newest letter first
        int index = stream.charAt(i) - 'a';
        if (node.children[index] == null) {
          return false; // MISS: no word ends with these letters
        }
        node = node.children[index];
        if (node.isEndOfWord) {
          return true; // MATCH: a word is a suffix of the stream
        }
      }
      return false;
    }
  }
}
