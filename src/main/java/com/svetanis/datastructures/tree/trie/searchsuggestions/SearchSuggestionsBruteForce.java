package com.svetanis.datastructures.tree.trie.searchsuggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggestions System

public final class SearchSuggestionsBruteForce {
  // Time Complexity: O(m * n + n log n)
  // Space Complexity: O(n)

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products);
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1);
      List<String> list = new ArrayList<>();
      for (String product : products) {
        if (product.startsWith(prefix)) {
          list.add(product);
          if (list.size() == 3) {
            break;
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
    Print.print(system.suggestedProducts(products1, "mouse"));

    String[] products2 = { "havana" };
    SearchSuggestionsBruteForce system2 = new SearchSuggestionsBruteForce();
    Print.print(system2.suggestedProducts(products2, "havana"));
  }
}