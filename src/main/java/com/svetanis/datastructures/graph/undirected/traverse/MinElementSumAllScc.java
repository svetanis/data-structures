package com.svetanis.datastructures.graph.undirected.traverse;

import static java.lang.Math.min;

import com.svetanis.datastructures.graph.undirected.Graph;

// Sum of the minimum elements in all connected components of an undirected graph.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1. No statement fixes the sizes; this is not a
// LeetCode problem and has not been run against a judge. The element of a vertex is its number.
// Returns the sum, over every connected component, of the smallest vertex number in it. A vertex
// with no edge is a component of its own, and vertex 0 is a vertex like any other: a graph meant
// for the numbers 1 .. N has to be built with N + 1 vertices, and vertex 0 then adds 0.
//
// The one idea: the outer loop meets the vertices in increasing order, so the first unvisited
// vertex v is the smallest one of its component -- every smaller vertex was met before and, if
// it were in this component, would have walked all of it already. The walk from v still
// compares each neighbour it sees with the running minimum, which never goes below v here; the
// minimum is held in a one-element array so the recursion can update it.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, plus the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. MinElementSumAllSccIterative is the same sum with
// an explicit stack.
//
// Siblings: MinElementSumAllSccIterative -- same sum, explicit stack.

public final class MinElementSumAllScc {

  public static int dfs(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int sum = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        int[] min = {v}; // BASE: v is the smallest vertex of its component
        explore(g, v, visited, min);
        sum += min[0]; // TAKE: the component's minimum
      }
    }
    return sum;
  }

  private static void explore(Graph g, int v, boolean[] visited, int[] min) {
    visited[v] = true;
    for (int u : g.adj(v)) {
      min[0] = min(min[0], u); // RECORD: the smallest vertex seen in this component
      if (!visited[u]) {
        explore(g, u, visited, min);
      }
    }
  }

  public static void main(String[] args) {
    Graph g1 = new Graph(11);
    g1.addEdge(1, 2);
    g1.addEdge(3, 4);
    g1.addEdge(5, 6);
    g1.addEdge(7, 8);
    g1.addEdge(9, 10);

    // components {0}, {1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}: 0 + 1 + 3 + 5 + 7 + 9
    System.out.println(dfs(g1)); // 25

    Graph g2 = new Graph(7); // the numbers 1 .. 6, so 7 vertices
    g2.addEdge(1, 4);
    g2.addEdge(4, 5);

    // components {0}, {1, 4, 5}, {2}, {3}, {6}: 0 + 1 + 2 + 3 + 6
    System.out.println(dfs(g2)); // 12
  }
}
