package com.svetanis.datastructures.tree.trie.searchsuggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1268. Search Suggestions System
//
// Input: an array of distinct product names and a search word. The word is typed one letter at
// a time. On LeetCode: 1 <= products.length <= 1000, 1 <= products[i].length <= 3000,
// 1 <= sum(products[i].length) <= 2 * 10^4, all products distinct, 1 <= searchWord.length <= 1000,
// lowercase letters only.
// Returns one list per letter typed: the (at most) three products that begin with the letters
// typed so far, smallest in dictionary order first; an empty list when none does. This method
// sorts the products array in place.
//
// The one idea: sorted, the products that share any prefix form one unbroken block, already in
// alphabetical order, so the answer for a prefix is the first three products of its block. A
// lower-bound binary search finds where the block starts: the first product not smaller than the
// prefix. A product smaller than the prefix cannot start with it, and after the first product that
// does not start with it none can, so the take loop starts at that position and stops at the first
// miss or after three.
//
// Time: O(n log n) comparisons for the sort, then O(L * log n) comparisons for the searches -- n
// products, L = searchWord.length; each comparison and each startsWith costs up to O(L) letters
// Space: O(L) for the prefix being compared, besides the output
//
// Siblings: SearchSuggestionsBruteForce scans every product for every prefix;
// SearchSuggestionsTwoPointers narrows one window as the prefix grows; SearchSuggestionsTrieCursor
// and SearchSuggestionsTrieFromRoot keep the three best in each trie node.

public final class SearchSuggestionsBinarySearch {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products); // SORT: a block of equal prefixes becomes contiguous
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1); // the letters typed so far
      suggestions.add(firstThree(products, prefix, lowerBound(products, prefix))); // FIND the block, TAKE its head
    }
    return suggestions;
  }

  // the first position whose product is not smaller than prefix;
  // products.length - 1 when every product is smaller
  private static int lowerBound(String[] products, String prefix) {
    int left = 0;
    int right = products.length - 1;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (products[mid].compareTo(prefix) < 0) {
        left = mid + 1; // SKIP: smaller, cannot start the block
      } else {
        right = mid; // KEEP: not smaller, may be the start
      }
    }
    return left;
  }

  private static List<String> firstThree(String[] products, String prefix, int start) {
    List<String> list = new ArrayList<>();
    for (int j = start; j < products.length && j < start + 3 && products[j].startsWith(prefix); j++) {
      list.add(products[j]); // TAKE: up to three, stopping at the first product off the block
    }
    return list;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsBinarySearch system = new SearchSuggestionsBinarySearch();
    System.out.println(system.suggestedProducts(products1, "mouse")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]

    String[] products2 = { "havana" };
    SearchSuggestionsBinarySearch system2 = new SearchSuggestionsBinarySearch();
    System.out.println(system2.suggestedProducts(products2, "havana")); // [[havana], [havana], [havana], [havana], [havana], [havana]]

    String[] products3 = { "mobile", "mouse", "moneypot", "monitor", "mousepad", "art" };
    SearchSuggestionsBinarySearch system3 = new SearchSuggestionsBinarySearch();
    System.out.println(system3.suggestedProducts(products3, "mozart")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [], [], [], []]

    String[] products4 = { "bags", "baggage", "banner", "box", "cloths" };
    SearchSuggestionsBinarySearch system4 = new SearchSuggestionsBinarySearch();
    System.out.println(system4.suggestedProducts(products4, "bags")); // [[baggage, bags, banner], [baggage, bags, banner], [baggage, bags], [bags]]

    String[] products5 = { "havana" };
    SearchSuggestionsBinarySearch system5 = new SearchSuggestionsBinarySearch();
    System.out.println(system5.suggestedProducts(products5, "tatiana")); // [[], [], [], [], [], [], []]
  }
}
