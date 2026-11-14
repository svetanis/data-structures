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
// The one idea: sorted, the products that begin with the letters typed so far form one unbroken
// window [left, right], and each new letter can only shrink it. So keep the window and shrink it
// from both ends: move left forward past every product that is too short to have a letter at
// position i or has the wrong letter there, and move right backward the same way. What is left
// still begins with the whole prefix; its first three products are the answer. The two pointers
// only ever move inward, so across all letters they travel at most n steps in total. Once the
// window is empty (left > right) it stays empty and every later answer is [].
//
// Time: O(n log n) comparisons for the sort, then O(n + L) pointer moves in all -- n products,
// L = searchWord.length; each move compares one letter
// Space: O(1) besides the sort and the output
//
// Siblings: SearchSuggestionsBinarySearch finds the start of the block by binary search for each
// prefix; SearchSuggestionsBruteForce rescans every product; SearchSuggestionsTrieCursor and
// SearchSuggestionsTrieFromRoot keep the three best in each trie node.

public final class SearchSuggestionsTwoPointers {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    int left = 0; // first product still inside the window
    int right = products.length - 1; // last product still inside the window
    Arrays.sort(products); // SORT: the products with one prefix sit side by side
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i); // the letter just typed
      while (left <= right && (products[left].length() <= i || products[left].charAt(i) != c)) {
        left++; // SHRINK from the left: too short, or the wrong letter at position i
      }
      while (left <= right && (products[right].length() <= i || products[right].charAt(i) != c)) {
        right--; // SHRINK from the right, the same test
      }
      List<String> list = new ArrayList<>();
      for (int j = left; j < Math.min(left + 3, right + 1); j++) {
        list.add(products[j]); // TAKE: the first three of the window, fewer if it is smaller
      }
      suggestions.add(list);
    }
    return suggestions;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsTwoPointers system = new SearchSuggestionsTwoPointers();
    System.out.println(system.suggestedProducts(products1, "mouse")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]

    String[] products2 = { "havana" };
    SearchSuggestionsTwoPointers system2 = new SearchSuggestionsTwoPointers();
    System.out.println(system2.suggestedProducts(products2, "havana")); // [[havana], [havana], [havana], [havana], [havana], [havana]]

    String[] products3 = { "bags", "baggage", "banner", "box", "cloths" };
    SearchSuggestionsTwoPointers system3 = new SearchSuggestionsTwoPointers();
    System.out.println(system3.suggestedProducts(products3, "bags")); // [[baggage, bags, banner], [baggage, bags, banner], [baggage, bags], [bags]]

    String[] products4 = { "havana" };
    SearchSuggestionsTwoPointers system4 = new SearchSuggestionsTwoPointers();
    System.out.println(system4.suggestedProducts(products4, "tatiana")); // [[], [], [], [], [], [], []]
  }
}