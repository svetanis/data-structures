package com.svetanis.datastructures.heap;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

// K most frequent words, with their counts (no LeetCode number)
//
// Input: a list of words (case-sensitive: "Fee" and "fee" are two words) and k, at least 1 and
// at most the number of distinct words. Returns k (word, count) pairs for words that occur most
// often, from the most frequent down. This is LeetCode 692's input without its tie rule: when
// several words share the count at the k-th place, which of them are kept, and their order among
// themselves, depends on the order the words were first seen.
//
// The one idea: count every word, then keep a bounded heap. One heap entry is one word with its
// count, and the heap is a MIN-heap on the count, so its top is the least frequent word kept so
// far -- the one to throw away when a (k + 1)-th arrives. After every word has been offered, the
// heap holds k words and none of the words thrown out is more frequent than any word kept.
// Polling hands back the least frequent first, so each one goes to the front of the answer.
//
// Time: O(n + d log k) -- counting is one pass over the n words; each of the d distinct words is
// offered once to a heap that never holds more than k + 1 entries.
// Space: O(d + k) -- the count map and the heap.
//
// Siblings: TopKFrequentWordsLexSorted (this package) solves LeetCode 692 itself -- the same heap
// with an alphabetical second key, words only. KMostFrequentTerms (algorithms,
// search.kmostfrequent) is this heap without the counts, least frequent first.

public final class TopKFrequentWords {

  public static List<Entry<String, Integer>> kMostFrequent(List<String> terms, int k) {
    Map<String, Integer> map = frequencyMap(terms); // COUNT every word

    // min heap : least frequent element first
    Queue<Entry<String, Integer>> queue = new PriorityQueue<>(comparing(e -> e.getValue())); // ORDER: count ascending

    // keep k top frequent elements in the heap
    for (Entry<String, Integer> entry : map.entrySet()) {
      queue.offer(entry); // ADD: every word goes in; k + 1 for an instant
      if (queue.size() > k) {
        queue.poll(); // EVICT the least frequent word kept
      }
    }
    LinkedList<Entry<String, Integer>> list = new LinkedList<>();
    while (!queue.isEmpty()) {
      Entry<String, Integer> entry = queue.poll(); // DRAIN: least frequent first
      list.addFirst(Map.entry(entry.getKey(), entry.getValue())); // REVERSE: most frequent ends up first
    }
    return list; // RETURN k (word, count) pairs
  }

  private static Map<String, Integer> frequencyMap(List<String> terms) {
    Map<String, Integer> map = new LinkedHashMap<>(); // FIRST-SEEN order: it settles the ties
    for (String term : terms) {
      map.put(term, map.getOrDefault(term, 0) + 1); // COUNT one more sighting of this word
    }
    return map;
  }

  // PRINT: one pair per line, then an empty line
  private static void printLines(List<Entry<String, Integer>> pairs) {
    for (Entry<String, Integer> pair : pairs) {
      System.out.println(pair);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(kMostFrequent(list1, k)); // ab=2, aaron=2, aaa=2 -- one per line, then an empty line

    List<String> list2 = build2();
    printLines(kMostFrequent(list2, k)); // Fee=5, Fo=4, Fi=3 -- one per line, then an empty line
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
