package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSorting.topologicalSort;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.min;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

// Given a sorted dictionary (array of words) of an alien language, 
// find order of characters in the language.

public final class OrderOfChars {

  public static ImmutableList<Character> sort(Graph g) {
    List<Integer> list = topologicalSort(g);
    return transform(list, i -> (char) (Character.valueOf('a') + i));
  }

  private static void createGraph(String[] words, Graph g) {
    int len = words.length;
    for (int i = 0; i < len - 1; i++) {
      // take the current two words and
      // find the first mismatching char
      String w1 = words[i];
      String w2 = words[i + 1];
      for (int j = 0; j < min(w1.length(), w2.length()); j++) {
        // if we find a mismatching char, then add an edge
        // from char of w1 to that of w2
        if (w1.charAt(j) != w2.charAt(j)) {
          int v1 = w1.charAt(j) - Character.valueOf('a');
          int v2 = w2.charAt(j) - Character.valueOf('a');
          g.addEdge(v1, v2);
          break;
        }
      }
    }
  }

  public static void main(String[] args) {
    String[] words = { "caa", "aaa", "aab" };
    Graph g = new Graph(3);
    createGraph(words, g);
    print(sort(g));
  }
}
