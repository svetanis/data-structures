package com.svetanis.datastructures.graph.directed.eulerian;

import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;

import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

// Can the given strings be chained into a circle?
//
// Input: list -- non-empty strings of lower-case letters. A string X may be put before a string Y if
// the last letter of X is the first letter of Y. Returns: true if every string can be used exactly
// once in a circle, each string followed by one it may go before, the last one followed by the
// first.
//
// The one idea: a string is an arrow from its first letter to its last letter, and the 26 letters
// are the points. Using every string once in a circle is walking along every arrow exactly once and
// ending where the walk began (an Eulerian circuit). Such a walk exists exactly when (1) every
// letter has as many arrows leaving it as arriving -- each time the walk comes into a letter it must
// leave it again -- and (2) every letter that has any arrow can be reached from any other, so the
// arrows form one piece. With (1) holding, reaching everything by following arrows from one letter
// is enough for (2).
//
// Time: O(n + 26) for n strings -- build the arrows, count them, one walk over the 26 letters.
// Space: O(n + 26) -- the arrows.
//
// Siblings: EulerianCircuit asks the same question of a graph that is already built, using a
// strong-connectivity test instead of the single walk used here.

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
      int v1 = str.charAt(0) - 'a'; // FIRST letter
      int v2 = str.charAt(str.length() - 1) - 'a'; // LAST letter
      g.addEdge(v1, v2); // ARROW: first -> last
    }
    return g;
  }

  private static boolean isEulerianCycle(Graph g) {
    int size = g.size();
    // check that all letters with an arrow are connected
    if (!isConnected(g)) {
      return false;
    }
    // check that every letter has as many arrows in as out
    int[] in = new int[size];
    for (int i = 0; i < size; ++i) {
      for (int u : g.adj(i)) {
        in[u]++; // COUNT the arrow i -> u at its head
      }
    }
    for (int i = 0; i < size; ++i) {
      if (in[i] != g.edges[i].size()) {
        return false;
      }
    }
    return true;
  }

  private static boolean isConnected(Graph g) {
    int size = g.size();

    // find a letter with an arrow leaving it
    int nonZeroDegree = -1;
    for (int i = 0; i < size; ++i) {
      if (g.edges[i].size() != 0) {
        nonZeroDegree = i;
        break;
      }
    }

    // no arrows at all: nothing to connect
    if (nonZeroDegree == -1) {
      return true;
    }

    // start a walk from that letter
    boolean[] visited = new boolean[size];
    dfs(g, nonZeroDegree, visited);

    // check that every letter with an arrow leaving it was reached
    for (int i = 0; i < size; ++i) {
      if (!visited[i] && g.edges[i].size() > 0) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    List<String> list1 = Arrays.asList("geek", "king");
    System.out.println(chained(list1)); // true -- geek, king: k -> g and g -> k

    List<String> list2 = Arrays.asList("for", "geek", "rig", "kaf");
    System.out.println(chained(list2)); // true -- for, rig, geek, kaf

    List<String> list3 = Arrays.asList("aab", "bac", "aaa", "cda");
    System.out.println(chained(list3)); // true -- aab, bac, cda, aaa

    List<String> list4 = Arrays.asList("aaa", "bbb", "baa", "aab");
    System.out.println(chained(list4)); // true -- aaa, aab, bbb, baa

    List<String> list5 = Arrays.asList("aaa");
    System.out.println(chained(list5)); // true -- a lone aaa follows itself

    List<String> list6 = Arrays.asList("aaa", "bbb");
    System.out.println(chained(list6)); // false -- a and b are never joined
  }
}
