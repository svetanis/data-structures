package com.svetanis.datastructures.tree.trie;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

public final class WordBreak {

  public static Optional<String> matchMultiple(Node root, String input) {

    Node node = root;
    String result = "";
    for (char c : input.toCharArray()) {
      Map<Character, Node> children = node.children;
      if (children.containsKey(c)) {
        node = children.get(c);
        result = result + c;
        if (node.leaf) {
          result = result + " ";
          node = root;

          // instead of going back to the root of the trie,
          // check if next character in the string matches
          // next character under current trie node.
          // this will help match "i like samsung"
          // along with "i like sam sung"
        }

      } else {
        return absent();
      }
    }
    return of(result);
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("i", "like", "sam", //
        "sung", "samsung", "mobile", "ice", //
        "cream", "icecream", "man", "go", "mango");

    Trie tree = Trie.build(list);
    Node root = tree.getRoot();
    System.out.println("ilikemango--> " + matchMultiple(root, "ilikemango"));
    System.out.println("ilikesamsung--> " + matchMultiple(root, "ilikesamsung"));
    System.out.println("iliketest--> " + matchMultiple(root, "iliketest"));
  }

}
