package com.svetanis.datastructures.tree.trie.prefix;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.svetanis.datastructures.tree.trie.Node;
import com.svetanis.datastructures.tree.trie.Trie;

// 14. Longest Common Prefix
//
// Input: a list of strings. On LeetCode: 1 <= strs.length <= 200,
// 0 <= strs[i].length <= 200, lowercase letters only.
// Returns the longest string that every word in the list begins with; "" when
// there is none.
//
// The one idea: put every word in a trie. A node holds one letter, and every word
// that passes through it shares the letters on the path from the root. So the common
// prefix is the path walked from the root while two things stay true: the node has
// exactly one child (every word goes the same way) and no word ends at it (a word
// that ends is the shortest word, so nothing longer can be shared). The first
// node where either fails is where the words part, and the letters collected so
// far are the answer.
//
// Time: O(S) -- S = total letters in all the words: building the trie costs one
// step per letter, and the walk down is at most as long as the shortest word
// Space: O(S) -- the trie holds up to one node per letter
//
// Siblings: string.common.LongestCommonPrefix14 and string.common.LongestCommonPrefix
// in the algorithms repo solve the same problem by comparing the strings directly,
// with no trie.

public final class LongestCommonPrefix {

  public static String lcp(List<String> list) {
    Node root = Trie.build(list).getRoot(); // BUILD: every word into the trie
    return lcp(root);
  }

  private static String lcp(Node root) {
    Node node = root;
    StringBuilder sb = new StringBuilder(); // the letters on the path so far
    while (node != null && !node.isEndOfWord() && node.children.size() == 1) { // ONE way on, no word ends here
      Iterator<Entry<Character, Node>> iter = node.children.entrySet().iterator();
      if (iter.hasNext()) {
        Entry<Character, Node> entry = iter.next(); // the only child
        sb.append(entry.getKey()); // TAKE its letter
        node = entry.getValue(); // STEP down
      }
    }
    return sb.toString(); // RETURN: "" when the words part at the root
  }

  public static void main(String[] args) {
    List<String> list1 = Arrays.asList("geeksforgeeks", "geeks", "geek", "geezer");
    List<String> list2 = Arrays.asList("apple", "ape", "april");
    List<String> list3 = Arrays.asList("flower", "flow", "flight");
    List<String> list4 = Arrays.asList("dog", "racecar", "car");
    System.out.println(lcp(list1)); // gee
    System.out.println(lcp(list2)); // ap
    System.out.println(lcp(list3)); // fl
    System.out.println(lcp(list4)); // (empty line)
  }
}
