package com.svetanis.datastructures.tree.trie.searchsuggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggestions System

// Sorted, the products sharing any prefix form one unbroken block, already in
// alphabetical order, so the answer for a prefix is the first three products
// of its block. A lower-bound binary search finds where the block starts: the
// first product not smaller than the prefix. A product smaller than the prefix
// cannot start with it, and after the first product that does not start with
// it none can, so the take loop starts at that position and stops at the first
// miss or after three.

public final class SearchSuggestionsBinarySearch {
  // n products, L = word length
  // Time Complexity: O(n log n) sort + O(L * L * log n) queries -- each of
  // the L prefixes is built in O(L) and compared O(log n) times
  // Space Complexity: O(L) for the prefix being compared

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products);
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1);
      suggestions.add(firstThree(products, prefix, lowerBound(products, prefix)));
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
        left = mid + 1; // smaller: cannot start the block
      } else {
        right = mid; // not smaller: may be the start, keep it
      }
    }
    return left;
  }

  private static List<String> firstThree(String[] products, String prefix, int start) {
    List<String> list = new ArrayList<>();
    for (int j = start; j < products.length && j < start + 3 && products[j].startsWith(prefix); j++) {
      list.add(products[j]);
    }
    return list;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsBinarySearch system = new SearchSuggestionsBinarySearch();
    Print.print(system.suggestedProducts(products1, "mouse"));

    String[] products2 = { "havana" };
    SearchSuggestionsBinarySearch system2 = new SearchSuggestionsBinarySearch();
    Print.print(system2.suggestedProducts(products2, "havana"));

    String[] products3 = { "mobile", "mouse", "moneypot", "monitor", "mousepad", "art" };
    SearchSuggestionsBinarySearch system3 = new SearchSuggestionsBinarySearch();
    Print.print(system3.suggestedProducts(products3, "mozart")); // empty from moz on
  }
}
