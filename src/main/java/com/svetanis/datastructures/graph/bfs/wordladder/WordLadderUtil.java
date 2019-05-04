package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public final class WordLadderUtil {

  // m = len of the word
  // how many options m * 26
  // 26+26+26

  // dictionary/hashmap: O(n^2*m)

  // permutation O(n * m * m ) *26

  // num of words in dict < m*26 -> dict/hashmap check if there exist a word
  // oneCharaway

  // num of words in dict > (*26 -> permutation

  public static boolean largeDictionary(String src, Set<String> set) {
    int size = set.size();
    int len = src.length();
    int charComparisons = len * 26;
    return size > charComparisons;
  }

  public static ImmutableList<String> neighbors(String src, Set<String> set, boolean largeDict) {
    if (largeDict) {
      return neighbors(src, set);
    }
    return oneCharAway(src, set);
  }

  private static ImmutableList<String> neighbors(String src, Set<String> set) {
    List<String> list = newArrayList();
    for (int i = 0; i < src.length(); ++i) {
      for (char c = 'a'; c <= 'z'; ++c) {
        if (src.charAt(i) != c) {
          String str = src.substring(0, i) + c + src.substring(i + 1);
          if (!src.equals(str) && set.contains(str)) {
            list.add(str);
          }
        }
      }
    }
    return newList(list);
  }

  private static ImmutableList<String> oneCharAway(String src, Set<String> set) {
    List<String> list = newArrayList();
    for (String word : set) {
      if (!word.equals(src)) {
        if (oneCharAway(word, src)) {
          list.add(word);
        }
      }
    }
    return newList(list);
  }

  private static boolean oneCharAway(String src, String dst) {
    if (src.length() != dst.length() || src.equals(dst)) {
      return false;
    }
    int count = 0;
    for (int i = 0; i < src.length(); i++) {
      if (src.charAt(i) != dst.charAt(i)) {
        count++;
        if (count > 1) {
          return false;
        }
      }
    }
    return true;
  }

}
