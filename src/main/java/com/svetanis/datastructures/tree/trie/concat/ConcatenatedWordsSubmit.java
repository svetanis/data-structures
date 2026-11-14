package com.svetanis.datastructures.tree.trie.concat;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 472. Concatenated Words
//
// Input: an array of different words. Returns every word that is a concatenation of at least two
// shorter words of the array (a word may be used more than once). The order of the answer is
// free; this file returns the words shortest first. The array is sorted in place. On LeetCode:
// 1 <= words.length <= 10^4, 1 <= words[i].length <= 30, lowercase letters, all words different,
// the lengths add up to at most 10^5.
//
// The one idea: a concatenated word is built only from words shorter than itself, so handle the
// words shortest first and keep a trie of the words seen so far that were NOT concatenated. A
// concatenated word is itself made of those, so leaving it out loses no cut. dfs(word) walks the
// trie along the word; each time the walk stands on a word end, the rest of the word is tried
// from the root again, and an empty rest means the whole word was cut into trie words. A word
// equal to one already in the trie cannot occur, because the words are different, so the first
// piece is always shorter than the word and the cut always has at least two pieces.
//
// Time: O(sum of the cuts tried) -- one dfs call walks at most 30 letters and starts a new call at
// every word end on its path; a word of length l has at most 2^(l-1) ways to cut, far fewer
// in practice, and l <= 30
// Space: O(total letters * 26) for the trie, plus a recursion at most 30 deep
//
// Siblings: datastructures.todo.tree.trie.ConcatenatedWords is the same method; it differs
// only in its trie node class.

public final class ConcatenatedWordsSubmit {

  public static List<String> concat(String[] words) {
    Arrays.sort(words, comparingInt(a -> a.length())); // SORT: shortest first
    List<String> list = new ArrayList<>();
    Trie root = new Trie();
    for (String word : words) {
      if (dfs(root, word)) {
        list.add(word); // TAKE: cut into shorter words
      } else {
        root.insert(word); // SKIP into the trie: a building block for the longer words
      }
    }
    return list;
  }

  private static boolean dfs(Trie root, String word) {
    if (word.isEmpty()) {
      return true; // BASE: nothing left over, the cut is complete
    }
    Trie node = root;
    for (int i = 0; i < word.length(); i++) {
      int index = word.charAt(i) - 'a';
      if (node.children[index] == null) {
        return false; // MISS: no word starts like this
      }
      node = node.children[index]; // WALK one letter down
      // substring only where a word ends, not on every character
      if (node.isEndOfWord() && dfs(root, word.substring(i + 1))) {
        return true; // CUT here and cut the rest from the root
      }
    }
    return false;
  }

  public static void main(String[] args) {
    String[] a = { "cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat" };
    String[] a2 = { "cat", "dog", "catdog" };
    System.out.println(concat(a)); // [dogcatsdog, catsdogcats, ratcatdogcat]
    System.out.println(concat(a2)); // [catdog]
  }

  public static final class Trie {

    // true where a word ENDS, not where the node has no children
    private boolean endOfWord;
    private Trie[] children = new Trie[26];

    public void insert(String str) {
      Trie node = this;
      for (char c : str.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie(); // CREATE the missing letter
        }
        node = node.children[index];
      }
      node.endOfWord = true; // MARK: a whole word stops here
    }

    public boolean isEndOfWord() {
      return endOfWord;
    }
  }
}
