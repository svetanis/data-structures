package com.svetanis.datastructures.tree.trie.boldtag;

import java.util.ArrayList;
import java.util.List;

// 616. Add Bold Tag in String
//
// Input: a string s and an array of words. Returns s with every letter that lies inside an
// occurrence of some word wrapped in bold tags: occurrences that overlap or touch are wrapped
// together in one <b> ... </b>, so no two bold stretches are next to each other. On LeetCode:
// 1 <= s.length <= 1000, 0 <= words.length <= 100, 1 <= words[i].length <= 1000, s and the words
// are letters and digits (every character is below 128, the size of a trie node here).
//
// The one idea: one interval {start, end} holds the positions in s where one occurrence of a word
// lies, both ends inclusive. A trie of the words, walked from every position of s, reports each
// place a word ends as an interval. Intervals that overlap or touch (the next one starts at most
// one past the end of the current one) are merged into one, and each merged interval gets one pair
// of tags.
//
// Time: O(t + s * l + k) -- t letters to build the trie, s = s.length starts each walking at
// most l = longest word letters, and k intervals to merge and print (k is at most s * l)
// Space: O(128 * t + k) -- 128 child slots in each trie node, and the interval lists
//
// Siblings: AddBoldTagIndexOf marks a boolean per letter with String.indexOf and needs no merge.

public final class AddBoldTagTrie {

  public static String addBoldTag(String s, String[] words) {
    Trie root = init(words);
    List<int[]> intervals = intervals(s, root);
    if (intervals.isEmpty()) {
      return s;
    }
    List<int[]> merged = merge(intervals);
    return addTags(s, merged);
  }

  private static String addTags(String s, List<int[]> intervals) {
    StringBuilder sb = new StringBuilder();
    int index = 0;
    int interval = 0;
    int n = s.length();
    while (index < n) {
      if (interval == intervals.size()) {
        sb.append(s.substring(index)); // the rest of s is outside every interval
        break;
      }
      int start = intervals.get(interval)[0];
      int end = intervals.get(interval)[1];
      if (index < start) {
        sb.append(s.subSequence(index, start)); // COPY the letters before the interval
      }
      interval++;
      sb.append("<b>"); // TAG: the whole interval goes between one pair of tags
      sb.append(s.substring(start, end + 1));
      sb.append("</b>");
      index = end + 1;
    }
    return sb.toString();
  }

  private static List<int[]> merge(List<int[]> intervals) {
    List<int[]> list = new ArrayList<>();
    int start = intervals.get(0)[0];
    int end = intervals.get(0)[1];
    for (int i = 1; i < intervals.size(); i++) {
      int left = intervals.get(i)[0];
      int right = intervals.get(i)[1];
      if (end + 1 < left) {
        list.add(new int[] { start, end }); // GAP: the current interval is complete
        start = left;
        end = right;
      } else {
        end = Math.max(end, right); // MERGE: overlapping or touching, extend the end
      }
    }
    list.add(new int[] { start, end });
    return list;
  }

  private static List<int[]> intervals(String s, Trie root) {
    int n = s.length();
    List<int[]> intervals = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      Trie node = root;
      for (int j = i; j < n; j++) {
        char c = s.charAt(j);
        if (node.children[c] == null) {
          break; // no word continues with this letter
        }
        node = node.children[c]; // WALK one letter down
        if (node.endOfWord) {
          intervals.add(new int[] { i, j }); // RECORD: a word occupies positions i to j
        }
      }
    }
    return intervals;
  }

  private static Trie init(String[] words) {
    Trie root = new Trie();
    for (String word : words) {
      root.insert(word);
    }
    return root;
  }

  public static void main(String[] args) {
    String[] words = { "abc", "123" };
    System.out.println(addBoldTag("abcxyz123", words)); // <b>abc</b>xyz<b>123</b>
    String[] w2 = { "aaa", "aab", "bc" };
    System.out.println(addBoldTag("aaabbcc", w2)); // <b>aaabbc</b>c
    System.out.println(addBoldTag("abc", new String[0])); // abc
  }

  private static class Trie {
    private boolean endOfWord;
    private Trie[] children = new Trie[128];

    public void insert(String word) {
      Trie node = this;
      for (char c : word.toCharArray()) {
        if (node.children[c] == null) {
          node.children[c] = new Trie(); // CREATE the missing letter
        }
        node = node.children[c];
      }
      node.endOfWord = true; // MARK: a whole word stops here
    }
  }
}
