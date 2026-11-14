package com.svetanis.datastructures.tree.trie.prefix;

// 1698. Number of Distinct Substrings in a String
//
// Input: a string s. On LeetCode: 1 <= s.length <= 500, lowercase letters only.
// Returns the number of different substrings of s.
//
// The one idea: every substring is a prefix of some suffix of s, and a trie holds
// each prefix as one path from the root. One trie node stands for one distinct
// string -- the letters on the path from the root to it. So insert every suffix,
// letter by letter, and count the nodes that did not exist yet: each new node is
// a substring seen for the first time. A suffix that repeats an earlier path
// creates no node, which is how repeats are dropped without comparing any string.
// The trie holds exactly the answer in nodes -- 28 for a 7-letter string with no
// repeats. O(n) is the depth of one path down it, not the size of the tree.
//
// Time: O(n^2) -- n suffixes, each walked letter by letter, n * (n + 1) / 2 steps in all
// Space: O(n^2) -- one node per distinct substring, up to n * (n + 1) / 2 nodes
//
// Siblings: CountDistinctSubStr copies every substring into a HashSet instead; the
// answer is the same, the cost is O(n^3).

public final class CountDistinctSubStrTrie {

  private Trie root;

  public int countDistinct(String s) {
    this.root = new Trie(); // RESET: a second call on this object starts from an empty trie
    int len = s.length();
    int count = 0; // COUNT: nodes created = distinct substrings
    for (int start = 0; start < len; start++) { // one suffix per start
      Trie node = root;
      for (int end = start; end < len; end++) { // WALK the suffix, one letter per step
        int index = s.charAt(end) - 'a';
        if (node.children[index] == null) {
          count += 1; // NEW: this path was never walked, so this substring is new
          node.children[index] = new Trie();
        }
        node = node.children[index]; // STEP down; an existing node is a repeat, count nothing
      }
    }
    return count; // RETURN: the number of nodes below the root
  }

  public static void main(String[] args) {
    CountDistinctSubStrTrie cds = new CountDistinctSubStrTrie();
    System.out.println(cds.countDistinct("aabbaba")); // 21
    System.out.println(cds.countDistinct("abcdefg")); // 28
  }

  private static class Trie {

    public Trie[] children;

    public Trie() {
      this.children = new Trie[26];
    }
  }
}