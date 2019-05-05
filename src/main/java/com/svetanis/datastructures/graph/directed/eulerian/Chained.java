package com.svetanis.datastructures.graph.directed.eulerian;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;
import static com.svetanis.java.base.utils.Bits.isOdd;

import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

// Given an array of strings, find if the given strings can be chained to form a circle. 
// A string X can be put before another string Y in circle if the last character of X is same as first character of Y.

public final class Chained {

  private static final int CHARS = 26;

  public static boolean chained(List<String> list) {
    Graph g = createGraph(list);

    // the given array of strings can be chained
    // if there is an eulerian cycle in the graph
    return isEulerianCycle(g);
  }

  private static Graph createGraph(List<String> list) {
    Graph g = new Graph(CHARS);
    for (String str : list) {
      int v1 = str.charAt(0) - Character.valueOf('a');
      int v2 = str.charAt(str.length() - 1) - Character.valueOf('a');
      g.addEdge(v1, v2);
    }
    return g;
  }

  private static boolean isEulerianCycle(Graph g) {
    int size = g.size();
    // check if all non-zero degree vertices are connected
    if (!isConnected(g)) {
      return false;
    }
    // check if there is a vertex with odd degree
    for (int i = 0; i < size; ++i) {
      if (isOdd(g.edges[i].size())) {
        return false;
      }
    }
    // if all vertices are of even degree
    return true;
  }

  private static boolean isConnected(Graph g) {
    int size = g.size();

    // find a vertex with non-zero degree
    int nonZeroDegree = -1;
    for (int i = 0; i < size; ++i) {
      if (g.edges[i].size() != 0) {
        nonZeroDegree = i;
        break;
      }
    }

    // if there are no edges in the graph, return true
    if (nonZeroDegree == size) {
      return true;
    }

    // start DFS traversal from a vertex with non-zero degree
    boolean[] visited = new boolean[size];
    dfs(g, nonZeroDegree, visited);

    // check if all non-zero degree vertices are visited
    for (int i = 0; i < size; ++i) {
      if (!visited[i] && g.edges[i].size() > 0) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    List<String> list1 = newArrayList("geek", "king");
    System.out.println(chained(list1));

    List<String> list2 = newArrayList("for", "geek", "rig", "kaf");
    System.out.println(chained(list2));

    List<String> list3 = newArrayList("aab", "bac", "aaa", "cda");
    System.out.println(chained(list3));

    List<String> list4 = newArrayList("aaa", "bbb", "baa", "aab");
    System.out.println(chained(list4));

    List<String> list5 = newArrayList("aaa");
    System.out.println(chained(list5));

    List<String> list6 = newArrayList("aaa", "bbb");
    System.out.println(chained(list6));
  }
}
