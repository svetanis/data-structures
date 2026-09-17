package com.svetanis.datastructures.tree.trie.searchsuggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggestions System

// A TRIE THAT RESTARTS AT THE ROOT FOR EVERY PREFIX.
// kept as the baseline to read against
// SearchSuggestionsTrieCursor.java, which holds the
// cursor between prefixes and is otherwise the same file.
// a trie's advantage over sort-plus-binary-search is NOT
// lookup -- it is that consecutive prefixes share a path.
// re-entering at the root pays for the structure and then
// throws away what it bought.

public final class SearchSuggestionsTrieFromRoot {
  // n products, m = longest product, L = word length
  // Time Complexity: O(n * m * log n) sort + O(n * m) build
  // + O(L^2) query -- prefixes of length 1..L cost 1+2+...+L
  // node steps, where holding the cursor would cost L
  // Space Complexity: O(n * m)

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products);
    Trie trie = init(products);
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1);
      suggestions.add(trie.search(prefix));
    }
    return suggestions;
  }

  private Trie init(String[] products) {
    Trie trie = new Trie();
    for (String product : products) {
      trie.insert(product);
    }
    return trie;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsTrieFromRoot system = new SearchSuggestionsTrieFromRoot();
    Print.print(system.suggestedProducts(products1, "mouse"));

    String[] products2 = { "havana" };
    SearchSuggestionsTrieFromRoot system2 = new SearchSuggestionsTrieFromRoot();
    Print.print(system2.suggestedProducts(products2, "havana"));
  }

  private static class Trie {
    private Trie[] children = new Trie[26];
    private List<String> suggestions = new ArrayList<>();

    public void insert(String word) {
      Trie node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie();
        }
        node = node.children[index];
        if (node.suggestions.size() < 3) {
          node.suggestions.add(word);
        }
      }
    }

    public List<String> search(String word) {
      Trie node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          return new ArrayList<>();
        }
        node = node.children[index];
      }
      return node.suggestions;
    }
  }
}
