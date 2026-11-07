package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Sort terms by frequency (not a LeetCode problem)
//
// kMostFrequent(terms, k) takes a list of strings and returns each distinct string once,
// paired with how many times it appears, the most frequent first; strings with the same count
// come in alphabetical order (String.compareTo, so capitals before lower case). k is not used:
// every distinct string comes back, not only the first k.
//
// Count each string in a map, turn the map into (term, count) pairs, and sort the pairs with
// one comparator: higher count first, then the term. The counts are compared as int values:
// two boxed Integers compared with == are the same object only for the small values Java
// caches, so equal counts of 128 or more would look different and never reach the tie rule.
//
// Siblings, same problem: none in this package.
// Related: TopKFrequentWordsLexSorted in the algorithms repo (search/kmostfrequent), LC 692,
// keeps only the k most frequent words, with the same tie rule.

public final class SortByFrequency {
  // Time complexity: O(n + d log d), n strings counted, d distinct ones sorted
  // Auxiliary Space: O(d), one map entry and one pair per distinct string

  private static final Comparator<TermCount> BY_COUNT_THEN_TERM = Comparator
      .comparingInt(TermCount::count).reversed() // ORDER: higher count first, as int values
      .thenComparing(TermCount::term); // TIE: equal counts in alphabetical order

  public static List<TermCount> kMostFrequent(List<String> terms, int k) {
    Map<String, Integer> map = freqMap(terms);
    List<TermCount> pairs = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      pairs.add(new TermCount(entry.getKey(), entry.getValue())); // PAIR: each term with its count
    }
    pairs.sort(BY_COUNT_THEN_TERM);
    return List.copyOf(pairs);
  }

  private static Map<String, Integer> freqMap(List<String> terms) {
    Map<String, Integer> map = new HashMap<>();
    for (String term : terms) {
      map.merge(term, 1, Integer::sum); // COUNT
    }
    return map;
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(kMostFrequent(list1, k));

    List<String> list2 = build2();
    printLines(kMostFrequent(list2, k));
  }

  private static void printLines(List<TermCount> pairs) {
    for (TermCount pair : pairs) {
      System.out.println(pair);
    }
    System.out.println();
  }

  public record TermCount(String term, int count) {
  }

  private static List<String> build2() {
    List<String> list = new ArrayList<>();
    list.add("Fee");
    list.add("Fi");
    list.add("Fo");
    list.add("Fum");
    list.add("Fee");
    list.add("Fo");
    list.add("Fee");
    list.add("Fee");
    list.add("Fo");
    list.add("Fi");
    list.add("Fi");
    list.add("Fo");
    list.add("Fum");
    list.add("Fee");
    return List.copyOf(list);
  }

  private static List<String> build1() {
    List<String> list = new ArrayList<>();
    list.add("ability");
    list.add("abortion");
    list.add("aaron");
    list.add("ab");
    list.add("absence");
    list.add("abortion");
    list.add("aa");
    list.add("a");
    list.add("able");
    list.add("aa");
    list.add("aaa");
    list.add("aberdeen");
    list.add("ab");
    list.add("abc");
    list.add("aaa");
    list.add("a");
    list.add("aaron");
    list.add("absent");
    list.add("abraham");
    list.add("able");
    return List.copyOf(list);
  }

}
