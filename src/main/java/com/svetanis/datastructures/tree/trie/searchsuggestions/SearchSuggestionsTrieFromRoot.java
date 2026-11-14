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
// A TRIE THAT RESTARTS AT THE ROOT FOR EVERY PREFIX.
// Kept as the baseline to read against SearchSuggestionsTrieCursor.java, which holds the
// cursor between prefixes and is otherwise the same file. A trie's advantage over
// sort-plus-binary-search is NOT lookup -- it is that consecutive prefixes share a path.
// Re-entering at the root pays for the structure and then throws away what it bought.
//
// The one idea: a trie node is a prefix, and each node keeps the three smallest products that
// pass through it. Products are inserted in sorted order, so the first three to arrive at a node
// are its three smallest and the node just stops accepting after three. To answer one prefix,
// walk its letters down from the root and return the list on the node reached; a missing child
// means no product begins with it, so [].
//
// Time: O(N log n) comparisons for the sort, O(N) for the build, O(L^2) for the queries --
// n products, N = total letters in all products, L = searchWord.length; prefixes of length
// 1..L cost 1+2+...+L node steps, where holding the cursor would cost L
// Space: O(N) -- up to one node per letter, each holding at most three product references
//
// Siblings: SearchSuggestionsTrieCursor holds the node between prefixes;
// SearchSuggestionsBinarySearch, SearchSuggestionsTwoPointers and SearchSuggestionsBruteForce work on
// the sorted array with no trie.

public final class SearchSuggestionsTrieFromRoot {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products); // SORT: the first three products to reach a node are its smallest
    Trie trie = init(products);
    List<List<String>> suggestions = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      String prefix = word.substring(0, i + 1); // the letters typed so far
      suggestions.add(trie.search(prefix)); // WALK the whole prefix again from the root
    }
    return suggestions;
  }

  private Trie init(String[] products) {
    Trie trie = new Trie();
    for (String product : products) {
      trie.insert(product); // BUILD: every product, in sorted order
    }
    return trie;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SearchSuggestionsTrieFromRoot system = new SearchSuggestionsTrieFromRoot();
    System.out.println(system.suggestedProducts(products1, "mouse")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]

    String[] products2 = { "havana" };
    SearchSuggestionsTrieFromRoot system2 = new SearchSuggestionsTrieFromRoot();
    System.out.println(system2.suggestedProducts(products2, "havana")); // [[havana], [havana], [havana], [havana], [havana], [havana]]

    String[] products3 = { "bags", "baggage", "banner", "box", "cloths" };
    SearchSuggestionsTrieFromRoot system3 = new SearchSuggestionsTrieFromRoot();
    System.out.println(system3.suggestedProducts(products3, "bags")); // [[baggage, bags, banner], [baggage, bags, banner], [baggage, bags], [bags]]

    String[] products4 = { "havana" };
    SearchSuggestionsTrieFromRoot system4 = new SearchSuggestionsTrieFromRoot();
    System.out.println(system4.suggestedProducts(products4, "tatiana")); // [[], [], [], [], [], [], []]
  }

  private static class Trie {
    private Trie[] children = new Trie[26];
    private List<String> suggestions = new ArrayList<>(); // the three smallest products through this node

    public void insert(String word) {
      Trie node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie(); // GROW: first product to use this letter here
        }
        node = node.children[index]; // STEP down
        if (node.suggestions.size() < 3) {
          node.suggestions.add(word); // KEEP: only the first three arrivals
        }
      }
    }

    public List<String> search(String word) {
      Trie node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          return new ArrayList<>(); // MISS: no product begins with this prefix
        }
        node = node.children[index]; // STEP down
      }
      return node.suggestions; // RETURN: the three kept on the node where the prefix ends
    }
  }
}
