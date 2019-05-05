package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class AcyclicGraphDfs {
  // Time Complexity: O(V + E)

  private static int V = 5;

  public static void dfs(Graph g) {
    dfs(g, 1, 0);
  }

  private static void dfs(Graph g, int src, int parent) {
    System.out.print(src + " ");
    for (int u : g.adj(src)) {
      if (u != parent) {
        dfs(g, u, src);
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(V + 1);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 5);

    dfs(g);
  }
}
