package com.svetanis.datastructures.heap;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

// 692. Top K Frequent Words
//
// Input: a list of words and k. Returns the k most frequent words, sorted by frequency from
// highest to lowest; words with the same frequency are sorted alphabetically (lexicographically
// ascending). The order is part of the answer, and so is the tie rule. On LeetCode:
// 1 <= words.length <= 500, 1 <= words[i].length <= 10, lowercase English letters, and k is
// between 1 and the number of distinct words.
//
// The one idea: count every word, then keep a bounded heap with a two-part key. One heap entry
// is one word; the heap reads its count from the map. The heap's top is the survivor to throw
// away, so the key is read from the throw-away end: lowest count first, and among equal counts
// the alphabetically LAST word first. Add-then-trim keeps the whole rule in the comparator.
// Polling hands back the worst survivor first, so each one is pushed to the front of the answer.
//
// Time: O(n + d log k) -- counting is one pass over the n words; each of the d distinct words is
// offered once to a heap that never holds more than k + 1 words (comparing two words costs their
// length).
// Space: O(d + k) -- the map and the heap.
//
// Siblings: TopKFrequentWordsLexSorted (algorithms, search.kmostfrequent) is the same comparator
// and the same drain. TopKFrequentWords (this package) has no tie rule and returns the counts
// too.

public final class TopKFrequentWordsLexSorted {

  public static List<String> topKFrequent(List<String> terms, int k) {
    Map<String, Integer> map = frequencyMap(terms); // COUNT every word

    // min heap : least frequent element first
    Queue<String> queue = new PriorityQueue<>(
        comparingInt((String s) -> map.get(s)).thenComparing(reverseOrder())); // ORDER: count asc, then word desc

    // keep k top frequent elements in the heap
    for (String entry : map.keySet()) {
      queue.offer(entry); // ADD: every word goes in; k + 1 for an instant
      if (queue.size() > k) {
        queue.poll(); // EVICT the worst survivor: fewest, then alphabetically last
      }
    }
    LinkedList<String> list = new LinkedList<>();
    while (!queue.isEmpty()) {
      list.addFirst(queue.poll()); // REVERSE: the worst survivor ends up last
    }
    return list; // RETURN: most frequent first, ties alphabetical
  }

  private static Map<String, Integer> frequencyMap(List<String> terms) {
    Map<String, Integer> map = new HashMap<>();
    for (String term : terms) {
      map.put(term, map.getOrDefault(term, 0) + 1); // COUNT one more sighting of this word
    }
    return map;
  }

  // PRINT: one word per line, then an empty line
  private static void printLines(List<String> words) {
    for (String word : words) {
      System.out.println(word);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(topKFrequent(list1, k)); // a, aa, aaa -- one per line, then an empty line

    List<String> list2 = build2();
    printLines(topKFrequent(list2, k)); // Fee, Fo, Fi -- one per line, then an empty line
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
    return list;
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
    return list;
  }

}
