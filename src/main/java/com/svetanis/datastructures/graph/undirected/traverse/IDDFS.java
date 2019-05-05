package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Iterative Deepening Search(IDS) or Iterative Deepening Depth First Search(IDDFS)
// The space required by DFS is O(d) where d is depth of tree, 
// but space required by BFS is O(n) where n is number of nodes in tree 
// IDDFS combines depth-first search’s space-efficiency and breadth-first search’s fast search (for nodes closer to root).
// IDDFS calls DFS for different depths starting from an initial value. 
// In every call, DFS is restricted from going beyond given depth. 
// So basically we do DFS in a BFS fashion.

public final class IDDFS {
  // Time Complexity: O(V + E)

  public static boolean iddfs(Graph g, int src, int dst, int d) {
    for (int i = 0; i <= d; i++) {
      if (dls(g, src, dst, i)) {
        return true;
      }
    }
    return false;
  }

  private static boolean dls(Graph g, int src, int dst, int d) {
    if (src == dst) {
      return true;
    }
    if (d <= 0) {
      return false;
    }

    for (int u : g.adj(src)) {
      if (dls(g, u, dst, d - 1)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 5);
    g.addEdge(2, 6);

    System.out.println(iddfs(g, 0, 6, 3));
  }
}
