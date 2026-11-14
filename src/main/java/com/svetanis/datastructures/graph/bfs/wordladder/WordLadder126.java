package com.svetanis.datastructures.graph.bfs.wordladder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// 126. Word Ladder II
//
// Input: src (beginWord), dst (endWord), words (wordList). A ladder changes one letter at a time,
// and every word after src must be in the word list. Returns every shortest ladder from src to
// dst, as lists of words, or an empty list if there is none. On LeetCode: 1 <= beginWord.length
// <= 5, endWord.length == beginWord.length, 1 <= wordList.length <= 500, lowercase letters,
// beginWord != endWord, all words in wordList are different.
//
// The one idea: a breadth-first search from src finds how far each word is, and while it does,
// it records for each word the words one level closer to src that reach it (map: word ->
// its parents). dist[w] is the level at which w was first reached. A queue entry is one word;
// the search handles one whole level at a time and stops after the level where dst appears,
// so every word that reaches dst on a shortest ladder has been recorded. A word leaves the
// working set when first reached, so later levels cannot reach it; but another word of the
// same level may still reach it, and the check dist[s] == steps adds that word as one more
// parent. At the end, walking back from dst through the parents, every way down to src is one
// shortest ladder.
//
// Time: O(n * 26 * m * m) for the search -- n words, each expanded into 26 * m candidates built
// and looked up in O(m) -- plus the size of the answer, since each ladder is built word by word.
// Space: O(n * m) for the sets and maps, plus the answer.

public final class WordLadder126 {

  public List<List<String>> ladder(String src, String dst, List<String> words) {
    List<List<String>> lists = new ArrayList<>();
    Set<String> set = new HashSet<>(words);
    if (!set.contains(dst)) {
      return lists; // BASE: dst is not a word you may reach
    }
    set.remove(src);
    Map<String, Integer> dist = new HashMap<>(); // word -> level at which it was first reached
    dist.put(src, 0);
    Map<String, Set<String>> map = new HashMap<>(); // word -> the words one level closer that reach it
    Queue<String> queue = new ArrayDeque<>();
    queue.offer(src);

    boolean endWord = false;
    int steps = 0;

    while (!queue.isEmpty() && !endWord) { // WALK: level by level, until dst is reached
      steps++;
      for (int i = queue.size(); i > 0; i--) { // WALK: every word of this level
        String word = queue.poll();
        char[] chars = word.toCharArray();
        for (int j = 0; j < chars.length; j++) {
          char original = chars[j];
          for (char c = 'a'; c <= 'z'; c++) {
            chars[j] = c;
            String s = new String(chars);
            if (dist.getOrDefault(s, 0) == steps) {
              map.get(s).add(word); // RECORD: another parent at the same level
            }
            if (!set.contains(s)) {
              continue; // SKIP: not a word, or already reached on an earlier level
            }
            map.computeIfAbsent(s, k -> new HashSet<>()).add(word); // RECORD: first parent
            set.remove(s);
            queue.offer(s);
            dist.put(s, steps);
            if (dst.equals(s)) {
              endWord = true; // finish this level, then stop
            }
          }
          chars[j] = original;
        }
      }
    }
    if (endWord) {
      Deque<String> path = new ArrayDeque<>();
      path.add(dst);
      backtrack(path, src, dst, lists, map);
    }
    return lists; // RETURN: all the shortest ladders
  }

  private void backtrack(Deque<String> path, String src, String word, List<List<String>> lists,
      Map<String, Set<String>> map) {
    if (word.equals(src)) { // BASE: reached src, the path is a whole ladder
      lists.add(new ArrayList<>(path)); // RECORD: a copy, since path keeps changing
      return;
    }
    for (String s : map.get(word)) { // WALK: every parent of word
      path.addFirst(s); // TAKE
      backtrack(path, src, s, lists, map);
      path.removeFirst(); // UNDO: try the next parent
    }
  }

  public static void main(String[] args) {
    WordLadder126 wl = new WordLadder126();
    List<String> list = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    System.out.println(wl.ladder("hit", "cog", list)); // [[hit, hot, lot, log, cog], [hit, hot, dot, dog, cog]]

    WordLadder126 wll = new WordLadder126();
    List<String> list1 = Arrays.asList("hot", "dot", "dog", "lot", "log");
    System.out.println(wll.ladder("hit", "cog", list1)); // []
  }
}
