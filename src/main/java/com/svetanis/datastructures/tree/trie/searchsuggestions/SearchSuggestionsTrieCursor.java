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
// The one idea: a trie node is a prefix, and each node keeps the three smallest products that
// pass through it. Products are inserted in sorted order, so the first three to arrive at a node
// are its three smallest and the node never needs to compare anything: it just stops accepting
// after three. Typing the word is then a walk down the trie, and the answer for each letter is
// the list sitting on the node reached. THE INCREMENTAL CURSOR: SearchSuggestionsTrieFromRoot
// re-descends from the root for every prefix; this file holds the cursor between prefixes and
// changes nothing else. Prefixes of length 1..L cost L node steps here and 1+2+...+L there:
// L = 50 gives 50 steps against 1,275. This is the only thing that makes a trie beat
// sort-plus-binary-search: consecutive prefixes share a path. Wherever queries arrive as a stream
// of growing prefixes -- autocomplete, a typed query -- hold the node. Once a prefix misses, every
// longer prefix misses too, so the cursor stays null and the remaining answers are [].
//
// Time: O(N log n) comparisons for the sort, O(N) for the build, O(L) for the query -- n products,
// N = total letters in all products, L = searchWord.length
// Space: O(N) -- up to one node per letter, each holding at most three product references
//
// Siblings: SearchSuggestionsTrieFromRoot is the same trie searched from the root for each prefix;
// SearchSuggestionsBinarySearch, SearchSuggestionsTwoPointers and SearchSuggestionsBruteForce work on
// the sorted array with no trie.

public final class SearchSuggestionsTrieCursor {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products); // SORT: the first three products to reach a node are its smallest
    Trie trie = init(products);
    return trie.search(word);
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
    SearchSuggestionsTrieCursor system = new SearchSuggestionsTrieCursor();
    System.out.println(system.suggestedProducts(products1, "mouse")); // [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]

    String[] products2 = { "havana" };
    SearchSuggestionsTrieCursor system2 = new SearchSuggestionsTrieCursor();
    System.out.println(system2.suggestedProducts(products2, "havana")); // [[havana], [havana], [havana], [havana], [havana], [havana]]

    String[] products3 = { "bags", "baggage", "banner", "box", "cloths" };
    SearchSuggestionsTrieCursor system3 = new SearchSuggestionsTrieCursor();
    System.out.println(system3.suggestedProducts(products3, "bags")); // [[baggage, bags, banner], [baggage, bags, banner], [baggage, bags], [bags]]

    String[] products4 = { "havana" };
    SearchSuggestionsTrieCursor system4 = new SearchSuggestionsTrieCursor();
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

    public List<List<String>> search(String word) {
      List<List<String>> result = new ArrayList<>();
      Trie node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        // the cursor stays where the previous prefix left it,
        // so each extra character costs ONE step rather than
        // a fresh descent from the root
        if (node != null) {
          node = node.children[index]; // STEP: one letter further, from where the last prefix ended
        }
        // once a prefix misses, every longer prefix misses too
        result.add(node == null ? new ArrayList<>() : node.suggestions); // RECORD: this prefix's three
      }
      return result;
    }
  }
}
