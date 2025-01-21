package com.svetanis.datastructures.tree.trie.count;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggested System

public final class SuggestedSystem {

  public List<List<String>> suggestedProducts(String[] products, String word) {
    Arrays.sort(products);
    Trie trie = init(products);
    List<List<String>> suggestions = new ArrayList<>();
    for (List<Integer> indices : trie.search(word)) {
      List<String> list = new ArrayList<>();
      for (int index : indices) {
        list.add(products[index]);
      }
      suggestions.add(list);
    }
    return suggestions;
  }

  private Trie init(String[] products) {
    Trie trie = new Trie();
    for (int i = 0; i < products.length; i++) {
      trie.insert(products[i], i);
    }
    return trie;
  }

  public static void main(String[] args) {
    String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
    SuggestedSystem wd = new SuggestedSystem();
    Print.print(wd.suggestedProducts(products1, "mouse"));

    String[] products2 = { "havana" };
    SuggestedSystem wd2 = new SuggestedSystem();
    Print.print(wd2.suggestedProducts(products2, "havana"));
  }

  private static class Trie {
    private Trie[] children = new Trie[26];
    private List<Integer> indices = new ArrayList<>();

    public void insert(String word, int index) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int cid = word.charAt(i) - 'a';
        if (node.children[cid] == null) {
          node.children[cid] = new Trie();
        }
        node = node.children[cid];
        if (node.indices.size() < 3) {
          node.indices.add(index);
        }
      }
    }

    public List<Integer>[] search(String word) {
      Trie node = this;
      int n = word.length();
      List<Integer>[] result = new List[n];
      Arrays.setAll(result, k -> new ArrayList<>());
      for (int i = 0; i < n; i++) {
        int cid = word.charAt(i) - 'a';
        if (node.children[cid] == null) {
          break;
        }
        node = node.children[cid];
        result[i] = node.indices;
      }
      return result;
    }
  }
}