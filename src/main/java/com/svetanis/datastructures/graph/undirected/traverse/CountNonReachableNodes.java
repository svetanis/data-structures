package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class CountNonReachableNodes {
  // Time Complexity: O(V + E)

  public static int countNonReachable(Graph g, int src) {
    int n = g.size();
    int count = 0;
    boolean[] visited = new boolean[n];
    explore(g, src, visited);
    for (int v = 0; v < n; ++v) {
      if (!visited[v]) {
        count++;
      }
    }
    return count;
  }

  private static void explore(Graph g, int v, boolean[] visited) {
    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        explore(g, u, visited);
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

    System.out.println(countNonReachable(g, 2));
  }
}
