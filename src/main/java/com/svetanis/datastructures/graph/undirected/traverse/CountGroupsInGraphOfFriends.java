package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import com.svetanis.java.base.Pair;

public final class CountGroupsInGraphOfFriends {
  // Time Complexity: O(V + E)

  private static int V = 6;

  public static Pair<Integer, Integer> countGroups(Graph g) {
    int groups = 1;
    int components = 0;
    boolean[] visited = new boolean[V + 1];

    for (int v = 0; v < V; ++v) {
      if (!visited[v]) {
        components++;
        groups *= dfs(g, v, visited);
      }
    }
    if (components == 1) {
      groups = 0;
    }
    return Pair.build(components, groups);
  }

  public static int dfs(Graph g, int v, boolean[] visited) {
    visited[v] = true;
    int count = 1;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        count += dfs(g, u, visited);
      }
    }
    return count;
  }

  public static void main(String[] args) {
    Graph g = new Graph(V + 1);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(5, 6);

    System.out.println(countGroups(g));
  }
}
