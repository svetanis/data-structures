package com.svetanis.datastructures.tree.trie;

import java.util.HashMap;
import java.util.Map;

// 677. Map Sum Pairs
//
// Input: calls on a map from words to numbers. insert(key, val) stores val for key, replacing the
// old value if key was already there; sum(prefix) returns the total of the values of all keys that
// start with prefix. On LeetCode: 1 <= key.length, prefix.length <= 50, both are lowercase
// letters, 1 <= val <= 1000, at most 50 calls.
//
// The one idea: a trie of the keys in which one node, reached by a prefix, holds the sum of the
// values of all keys that start with that prefix. Inserting a key adds its value to every node on
// its path, so sum(prefix) is one walk to the prefix's node and a read. A key inserted twice must
// add only the change, new value minus old value, which is why a map remembers each key's value.
//
// Time: O(L) per insert and per sum -- L is the length of the key or prefix, one step per letter
// Space: O(total letters of all keys) -- 26 child slots in each node on a path, plus the map
//
// Siblings: none; Trie.java and impl/Trie.java use the same walk to answer a different question.

public final class MapSum {

  private Node root;
  private Map<String, Integer> map;

  public MapSum() {
    this.root = new Node();
    this.map = new HashMap<>();
  }

  public void insert(String key, int val) {
    int delta = val - map.getOrDefault(key, 0); // CHANGE: only what the key adds beyond its old value
    map.put(key, val); // RECORD the key's current value
    root.insert(key, delta);
  }

  public int sum(String prefix) {
    return root.search(prefix);
  }

  public static void main(String[] args) {
    MapSum ms = new MapSum();
    ms.insert("apple", 3);
    System.out.println(ms.sum("ap")); // 3
    ms.insert("app", 2);
    System.out.println(ms.sum("ap")); // 5
    ms.insert("apple", 10);
    System.out.println(ms.sum("ap")); // 12
    System.out.println(ms.sum("b")); // 0
  }

  private static class Node {
    private int val;
    private Node[] children;

    public Node() {
      this.children = new Node[26];
    }

    public void insert(String word, int val) {
      Node node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Node(); // CREATE the missing letter
        }
        node = node.children[index];
        node.val += val; // ADD the change to every prefix of the key
      }
    }

    public int search(String word) {
      Node node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          return 0; // MISS: no key starts with this prefix
        }
        node = node.children[index]; // WALK one letter down
      }
      return node.val; // RETURN: the sum kept at the prefix's node
    }
  }
}
