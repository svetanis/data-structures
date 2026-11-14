package com.svetanis.datastructures.tree.trie;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 140. Word Break II
//
// Input: a string s and a list of words dict. Returns every sentence that can be made by putting
// spaces into s so that each piece is a word of dict; a dict word may be used more than once.
// On LeetCode: 1 <= s.length <= 20, 1 <= dict.length <= 1000, 1 <= dict[i].length <= 10, s and the
// words are lowercase letters, the words are all different, and the number of sentences is at most
// 10^5.
//
// The one idea: dfs(s) holds the list of sentences, each a list of words, that spell exactly the
// text s. Try every prefix of s: when the prefix is a dict word, put it in front of each sentence
// that spells the rest of s. The empty text has exactly one sentence, the empty one, which is what
// lets the last word of a sentence close it. The trie holds the dict so that "is this prefix a
// word" is one walk down its letters.
//
// Time: O(2^n * n) worst case, n = s.length -- every way of cutting s is tried and the same rest
// of s is solved again each time it is reached; at n <= 20 that is small
// Space: O(2^n * n) for the sentences, plus the trie and a recursion n deep
//
// Siblings: WordBreakGreedy takes the first word it finds and so gives wrong answers on purpose;
// this file tries every prefix.
//
// Note: root is a field, so wordBreak starts by giving it a new trie; without that, a second call
// on the same object would also see the words of the first call.

public final class WordBreak140 {

  private TrieNode root = new TrieNode();

  public List<String> wordBreak(String s, List<String> dict) {
    root = new TrieNode(); // RESET: a new trie for every call, so an earlier dictionary cannot leak in
    for (String word : dict) {
      root.insert(word); // BUILD: the dictionary into the trie
    }
    List<List<String>> lists = dfs(s);
    return lists.stream().map(words -> String.join(" ", words)).collect(Collectors.toList());
  }

  private List<List<String>> dfs(String s) {
    List<List<String>> lists = new ArrayList<>();
    if ("".equals(s)) {
      lists.add(new ArrayList<>()); // BASE: the empty text has one sentence, the empty one
      return lists;
    }
    for (int i = 1; i <= s.length(); i++) {
      String ss = s.substring(0, i); // TRY: the first i letters as the first word
      if (root.search(ss)) {
        for (List<String> list : dfs(s.substring(i))) { // every sentence for the rest
          list.add(0, ss); // PUT the word in front
          lists.add(list);
        }
      }
    }
    return lists;
  }

  public static void main(String[] args) {
    WordBreak140 wb1 = new WordBreak140();
    List<String> list = asList("cat", "cats", "and", "sand", "dog");
    System.out.println(wb1.wordBreak("catsanddog", list)); // [cat sand dog, cats and dog]

    WordBreak140 wb2 = new WordBreak140();
    List<String> list1 = asList("apple", "pen", "applepen", "pine", "pineapple");
    System.out.println(wb2.wordBreak("pineapplepenapple", list1)); // [pine apple pen apple, pine applepen apple, pineapple pen apple]

    WordBreak140 wb3 = new WordBreak140();
    List<String> list2 = asList("cats", "dog", "sand", "and", "cat");
    System.out.println(wb3.wordBreak("catsandog", list2)); // []
  }

  private static class TrieNode {
    private TrieNode[] children = new TrieNode[26];
    private boolean endOfWord;

    public void insert(String word) {
      TrieNode node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode(); // CREATE the missing letter
        }
        node = node.children[index];
      }
      node.endOfWord = true; // MARK: a whole word stops here
    }

    public boolean search(String word) {
      TrieNode node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          return false; // MISS: no word has this letter next
        }
        node = node.children[index];
      }
      return node.endOfWord; // RETURN: a whole word, not just a prefix
    }
  }
}
