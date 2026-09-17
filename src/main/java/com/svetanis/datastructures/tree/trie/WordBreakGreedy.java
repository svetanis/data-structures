package com.svetanis.datastructures.tree.trie;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

// Word break, greedy: cut at the FIRST dictionary word the walk reaches,
// then start over from the root.
//
// WRONG ANSWERS, kept as the first step of the ladder. A shorter word can
// end where a longer one should have been taken:
//   dictionary {a, abc}, input "abc" -> cuts after "a", finds no word in
//   "bc", returns absent -- though "abc" is itself a word.
// Deciding at each cut whether to take the word or keep going is what
// LC 139 Word Break's DP does.

public final class WordBreakGreedy {

  public static Optional<String> matchMultiple(Node root, String input) {
    Node node = root;
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
      Map<Character, Node> children = node.children;
      if (!children.containsKey(c)) {
        return absent();
      }
      node = children.get(c);
      sb.append(c);
      if (node.endOfWord) {
        sb.append(' ');
        node = root; // the greedy step: the first word found is always taken
      }
    }
    if (node != root) {
      return absent(); // the input ended in the middle of a word
    }
    return of(sb.toString().trim());
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("i", "like", "sam", //
        "sung", "samsung", "mobile", "ice", //
        "cream", "icecream", "man", "go", "mango");
    Node root = Trie.build(list).getRoot();
    System.out.println(matchMultiple(root, "ilikemango")); // i like man go
    System.out.println(matchMultiple(root, "ilikesamsung")); // i like sam sung
    System.out.println(matchMultiple(root, "iliketest")); // absent
    System.out.println(matchMultiple(root, "ilikesa")); // absent -- "sa" is not a word

    Node small = Trie.build(newArrayList("a", "abc")).getRoot();
    System.out.println(matchMultiple(small, "abc")); // absent -- wrong, "abc" is a word
  }
}
