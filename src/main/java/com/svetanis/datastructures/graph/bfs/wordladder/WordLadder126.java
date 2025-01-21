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

public final class WordLadder126 {

  public List<List<String>> ladder(String src, String dst, List<String> words) {
    List<List<String>> lists = new ArrayList<>();
    Set<String> set = new HashSet<>(words);
    if (!set.contains(dst)) {
      return lists;
    }
    set.remove(src);
    Map<String, Integer> dist = new HashMap<>();
    dist.put(src, 0);
    Map<String, Set<String>> map = new HashMap<>();
    Queue<String> queue = new ArrayDeque<>();
    queue.offer(src);

    boolean endWord = false;
    int steps = 0;

    while (!queue.isEmpty() && !endWord) {
      steps++;
      for (int i = queue.size(); i > 0; i--) {
        String word = queue.poll();
        char[] chars = word.toCharArray();
        for (int j = 0; j < chars.length; j++) {
          char original = chars[j];
          for (char c = 'a'; c <= 'z'; c++) {
            chars[j] = c;
            String s = new String(chars);
            if (dist.getOrDefault(s, 0) == steps) {
              map.get(s).add(word);
            }
            if (!set.contains(s)) {
              continue;
            }
            map.computeIfAbsent(s, k -> new HashSet<>()).add(word);
            set.remove(s);
            queue.offer(s);
            dist.put(s, steps);
            if (dst.equals(s)) {
              endWord = true;
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
    return lists;
  }

  private void backtrack(Deque<String> path, String src, String word, List<List<String>> lists,
      Map<String, Set<String>> map) {
    if (word.equals(src)) {
      lists.add(new ArrayList<>(path));
      return;
    }
    for (String s : map.get(word)) {
      path.addFirst(s);
      backtrack(path, src, s, lists, map);
      path.removeFirst();
    }
  }

  public static void main(String[] args) {
    WordLadder126 wl = new WordLadder126();
    List<String> list = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    System.out.println(wl.ladder("hit", "cog", list)); //

    WordLadder126 wll = new WordLadder126();
    List<String> list1 = Arrays.asList("hot", "dot", "dog", "lot", "log");
    System.out.println(wll.ladder("hit", "cog", list1)); // []
  }
}
