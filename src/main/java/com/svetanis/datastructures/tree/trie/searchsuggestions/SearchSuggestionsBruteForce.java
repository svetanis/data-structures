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
// The one idea: sort the products once; then for each prefix, scan the sorted list from the
// front and keep the first three products that start with it. Sorted order means the first
// three found are the three smallest, so no comparison of candidates is needed. This is the
// plain baseline the other files improve on.
//
// Time: O(n log n) comparisons for the sort, then O(L * n) startsWith calls -- n products,
// L = searchWord.length: every prefix may scan all n products, each startsWith costing up to O(L)
// Space: O(L) for the prefix being compared, besides the output
//
// Siblings: SearchSuggestionsBinarySearch replaces the scan by a binary search for the start of
// the block; SearchSuggestionsTwoPointers narrows one window as the prefix grows;
// SearchSuggestionsTrieCursor and SearchSuggestionsTrieFromRoot keep the three best in each node.

public final class SearchSuggestionsBruteForce {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products); // SORT: the first three matches are then the three smallest
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1); // the letters typed so far
      List<String> list = new ArrayList<>();
      for (String product : products) { // SCAN from the front
        if (product.startsWith(prefix)) {
          list.add(product); // TAKE: a match
          if (list.size() == 3) {
            break; // STOP: three is all that is wanted
          }
        }
      }
      suggestions.add(list);
    }
    return suggestions;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsBruteForce system = new SearchSuggestionsBruteForce();
    System.out.println(system.suggestedProducts(products1, "mouse")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]

    String[] products2 = { "havana" };
    SearchSuggestionsBruteForce system2 = new SearchSuggestionsBruteForce();
    System.out.println(system2.suggestedProducts(products2, "havana")); // [[havana], [havana], [havana], [havana], [havana], [havana]]

    String[] products3 = { "bags", "baggage", "banner", "box", "cloths" };
    SearchSuggestionsBruteForce system3 = new SearchSuggestionsBruteForce();
    System.out.println(system3.suggestedProducts(products3, "bags")); // [[baggage, bags, banner], [baggage, bags, banner], [baggage, bags], [bags]]

    String[] products4 = { "havana" };
    SearchSuggestionsBruteForce system4 = new SearchSuggestionsBruteForce();
    System.out.println(system4.suggestedProducts(products4, "tatiana")); // [[], [], [], [], [], [], []]
  }
}