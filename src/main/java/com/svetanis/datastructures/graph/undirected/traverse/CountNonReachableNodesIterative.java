package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;

// Count the vertices that cannot be reached from a given vertex -- the same count as
// CountNonReachableNodes, with an explicit stack instead of recursion.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1 and a source vertex src. Not a LeetCode
// problem. Returns the number of vertices with no path from src; src itself is reachable.
//
// The one idea: the vertices that can be reached from src are exactly the ones a walk out from
// src marks, so mark them and count the unmarked ones. visited[v] says whether v has been
// reached; the stack holds vertices that are marked but whose neighbours are not read yet, and a
// vertex is marked when it is pushed, so it is pushed once.
//
// Time: O(V + E) -- each reachable vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the visited flags and the stack.
//
// Siblings: CountNonReachableNodes -- recursive; overflows the default stack on a path of about
// 10,000 vertices, where this version does not.

public final class CountNonReachableNodesIterative {

  public static int countNonReachable(Graph g, int src) {
    int n = g.size();
    int count = 0;
    boolean[] visited = new boolean[n];
    explore(g, src, visited); // WALK: mark everything src can reach
    for (int v = 0; v < n; ++v) {
      if (!visited[v]) {
        count++; // COUNT: no walk reached v
      }
    }
    return count;
  }

  private static void explore(Graph g, int src, boolean[] visited) {
    Deque<Integer> stack = new ArrayDeque<>();
    visited[src] = true;
    stack.push(src);
    while (!stack.isEmpty()) {
      int v = stack.pop();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true; // MARK when pushed, so u is pushed once
          stack.push(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(6, 7);

    // from 2 the walk reaches 0, 1 and 2; 3, 4, 5, 6 and 7 are left
    System.out.println(countNonReachable(g, 2)); // 5
  }
}
