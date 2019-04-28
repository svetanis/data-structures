package com.svetanis.datastructures.tree.trie;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public final class LongestMatchingPrefix {

  public static ImmutableMap<String, Optional<String>> lmp(List<String> list, List<String> words) {
    Map<String, Optional<String>> map = newHashMap();
    Node root = Trie.build(words).getRoot();
    for (String str : list) {
      checkedPut(map, str, lmp(root, str));
    }
    return newMap(map);
  }

  private static Optional<String> lmp(Node root, String input) {
    String result = "";
    int n = input.length();
    int prev = 0;
    Node node = root;
    for (int level = 0; level < n; ++level) {
      char c = input.charAt(level);
      Map<Character, Node> map = node.children;
      if (map.containsKey(c)) {
        result += c;
        node = map.get(c);
        if (node.leaf) {
          prev = level + 1;
        }
      } else {
        break;
      }
    }

    // if the last processed char didn't match end of a word,
    // return the previously matching prefix
    if (!node.leaf) {
      return of(result.substring(0, prev));
    } else {
      return isBlank(result) ? absent() : of(result);
    }
  }

  public static void main(String[] args) {
    List<String> base = newArrayList("are", "area", "base", "cat", "cater", "basement");
    List<String> list = newArrayList("caterer", "basement", "are", "arex", "basemexz", "xyz");
    System.out.println(lmp(list, base));
  }
}
