package com.svetanis.datastructures.graph.undirected.bipartite;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class BipartiteGraphDfs {
  // Time Complexity: O(V + E)

  public static boolean isBipartite(Graph g, int src) {
    int n = g.size();
    boolean[] visited = new boolean[n + 1];
    visited[src] = true;
    int[] colors = new int[n + 1];
    colors[src] = 0;
    return isBipartite(g, src, visited, colors);
  }

  private static boolean isBipartite(Graph g, int src, boolean[] visited, int[] colors) {
    for (int u : g.adj(src)) {
      if (!visited[u]) {
        visited[u] = true;
        colors[u] = 1 - colors[src];
        if (isBipartite(g, u, visited, colors)) {
          return false;
        }
      } else if (colors[u] == colors[src]) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 1);

    System.out.println(isBipartite(g, 1));
  }
}
