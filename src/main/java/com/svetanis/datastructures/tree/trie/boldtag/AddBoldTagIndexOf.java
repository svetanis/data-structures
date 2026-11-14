package com.svetanis.datastructures.tree.trie.boldtag;

// 616. Add Bold Tag in String
//
// Input: a string s and an array of words. Returns s with every letter that lies inside an
// occurrence of some word wrapped in bold tags: occurrences that overlap or touch are wrapped
// together in one <b> ... </b>, so no two bold stretches are next to each other. On LeetCode:
// 1 <= s.length <= 1000, 0 <= words.length <= 100, 1 <= words[i].length <= 1000, s and the words
// are letters and digits.
//
// The one idea: one cell of buffer says whether the letter at that position of s is bold. Mark
// every letter of every occurrence of every word as bold, then read buffer once from left to
// right: open a tag where a bold run starts, close it where the run ends. Overlapping and touching
// occurrences make one run on their own, because their cells are all true with no gap between.
//
// Time: O(w * s * l) worst case -- w words, s = s.length, l = longest word; each word can occur
// at up to s places, and each place costs up to l to compare and to mark
// Space: O(s) -- the buffer and the answer
//
// Siblings: AddBoldTagTrie gets the same answer by walking a trie from every position of s,
// then merging the intervals it finds.

public final class AddBoldTagIndexOf {

  public static String addBoldTag(String s, String[] words) {
    int n = words.length;
    if (n == 0) {
      return s;
    }
    boolean[] buffer = buffer(s, words);
    StringBuilder sb = new StringBuilder();
    boolean isAppend = false;
    for (int i = 0; i < buffer.length; i++) {
      if (buffer[i] && !isAppend) {
        sb.append("<b>"); // OPEN: a bold run starts here
        isAppend = true;
      }
      if (!buffer[i] && isAppend) {
        sb.append("</b>"); // CLOSE: the bold run ended before this letter
        isAppend = false;
      }
      sb.append(s.charAt(i));
    }
    if (isAppend) {
      sb.append("</b>"); // CLOSE: the run reaches the end of s
    }
    return sb.toString();
  }

  private static boolean[] buffer(String s, String[] words) {
    int n = words.length;
    int m = s.length();
    boolean[] buffer = new boolean[m];
    for (int i = 0; i < n; i++) {
      String word = words[i];
      int index = s.indexOf(word);
      while (index != -1) {
        for (int j = index; j < index + word.length(); j++) {
          buffer[j] = true; // MARK: this letter lies inside an occurrence
        }
        index = s.indexOf(word, index + 1); // NEXT occurrence, which may overlap this one
      }
    }
    return buffer;
  }

  public static void main(String[] args) {
    String[] words = { "abc", "123" };
    System.out.println(addBoldTag("abcxyz123", words)); // <b>abc</b>xyz<b>123</b>
    String[] w2 = { "aaa", "aab", "bc" };
    System.out.println(addBoldTag("aaabbcc", w2)); // <b>aaabbc</b>c
    System.out.println(addBoldTag("abc", new String[0])); // abc
  }
}
