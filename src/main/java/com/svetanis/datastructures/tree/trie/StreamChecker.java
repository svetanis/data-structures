package com.svetanis.datastructures.tree.trie;

// 1032. Stream of Characters

public final class StreamChecker {

  private TrieNode root;
  private StringBuilder stream;

  public StreamChecker(String[] words) {
    this.root = new TrieNode();
    for (String word : words) {
      this.root.insert(word);
    }
    this.stream = new StringBuilder();
  }

  public boolean query(char letter) {
    stream.append(letter);
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
      for (int i = word.length() - 1; i >= 0; i--) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode();
        }
        node = node.children[index];
      }
      node.isEndOfWord = true;
    }

    public boolean search(StringBuilder stream) {
      TrieNode node = this;
      // stream.length(), not stream.toString().length(): toString copies the whole stream on every query
      for (int i = stream.length() - 1; i >= 0; i--) {
        int index = stream.charAt(i) - 'a';
        if (node.children[index] == null) {
          return false;
        }
        node = node.children[index];
        if (node.isEndOfWord) {
          return true;
        }
      }
      return false;
    }
  }
}
