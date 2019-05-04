package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.largeDictionary;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.neighbors;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public final class WordLadder {

  public static ImmutableList<String> ladder(String src, String dst, Set<String> set) {
    if (src.equals(dst) && set.isEmpty()) {
      return newList();
    }
    boolean isLarge = largeDictionary(src, set);
    Queue<Node> queue = newLinkedList();
    queue.add(new Node(src, 0, null));
    set.add(dst);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      String w = node.word;
      for (String v : neighbors(w, set, isLarge)) {
        if (v.equals(dst)) {
          List<String> list = newArrayList();
          list.add(dst);
          Node parent = node;
          while (parent != null) {
            list.add(0, parent.word);
            parent = parent.prev;
          }
          return newList(list);
        }

        if (set.contains(v)) {
          queue.add(new Node(v, node.len + 1, node));
          set.remove(v);
        }
      }
    }
    return newList();
  }

  public static void main(String[] args) {
    Set<String> set = newHashSet("cccw", "accc", "accw");
    print(ladder("cccc", "cccc", set));
  }

  private static class Node {
    private int len;
    private String word;
    private Node prev;

    public Node(String word, int len, Node prev) {
      this.word = word;
      this.len = len;
      this.prev = prev;
    }
  }

}
