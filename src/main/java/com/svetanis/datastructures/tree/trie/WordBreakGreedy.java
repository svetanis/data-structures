package com.svetanis.datastructures.tree.trie;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Word break, greedy: cut at the FIRST dictionary word the walk reaches,
// then start over from the root.
//
// WRONG ANSWERS, kept on purpose as the naive first attempt. A shorter word can
// end where a longer one should have been taken:
//   dictionary {a, abc}, input "abc" -> cuts after "a", finds no word in
//   "bc", returns empty -- though "abc" is itself a word.
// Deciding at each cut whether to take the word or keep going is what
// LC 139 Word Break's DP does; LC 140 Word Break II lists every sentence.
//
// Input: the root of a trie of the dictionary, and a string input. Returns the words of the
// first greedy cut joined by single spaces, or empty when the walk gets stuck or the input ends
// inside a word. Not a LeetCode problem.
//
// The one idea: walk the trie letter by letter; the moment the walk stands on a node where a word
// ends, write a space and go back to the root. That choice is never reconsidered, which is why a
// shorter word taken too early makes the input unsolvable.
//
// Time: O(n) -- one trie step per letter of the input
// Space: O(n) -- the sentence being built
//
// Siblings: WordBreak140 tries every prefix and returns every sentence; this file is the greedy
// version of it.

public final class WordBreakGreedy {

  public static Optional<String> matchMultiple(Node root, String input) {
    Node node = root;
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
      Map<Character, Node> children = node.children;
      if (!children.containsKey(c)) {
        return Optional.empty(); // STUCK: no word continues with this letter
      }
      node = children.get(c); // WALK one letter down
      sb.append(c);
      if (node.endOfWord) {
        sb.append(' ');
        node = root; // the greedy step: the first word found is always taken
      }
    }
    if (node != root) {
      return Optional.empty(); // the input ended in the middle of a word
    }
    return Optional.of(sb.toString().trim()); // RETURN: the cut, spaces between the words
  }

  public static void main(String[] args) {
    List<String> list = Arrays.asList("i", "like", "sam", //
        "sung", "samsung", "mobile", "ice", //
        "cream", "icecream", "man", "go", "mango");
    Node root = Trie.build(list).getRoot();
    System.out.println(matchMultiple(root, "ilikemango")); // Optional[i like man go]
    System.out.println(matchMultiple(root, "ilikesamsung")); // Optional[i like sam sung]
    System.out.println(matchMultiple(root, "iliketest")); // Optional.empty
    System.out.println(matchMultiple(root, "ilikesa")); // Optional.empty -- "sa" is not a word

    Node small = Trie.build(Arrays.asList("a", "abc")).getRoot();
    System.out.println(matchMultiple(small, "abc")); // Optional.empty -- wrong, "abc" is a word
  }
}
