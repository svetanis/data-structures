package com.svetanis.datastructures.graph.undirected.traverse;

import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;
import static java.lang.Math.min;

import com.svetanis.datastructures.graph.undirected.Graph;
import com.svetanis.java.base.utils.IntWrapper;

// Sum of the minimum elements in all connected components of an undirected graph

public final class MinElementSumAllScc {
  // Time Complexity: O(V + E)

  public static int dfs(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int sum = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        IntWrapper min = newIntWrapper(v);
        explore(g, v, visited, min);
        sum += min.value;
      }
    }
    return sum;
  }

  private static void explore(Graph g, int v, boolean[] visited, IntWrapper min) {
    visited[v] = true;
    for (int u : g.adj(v)) {
      min.value = min(min.value, u);
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

    System.out.println(dfs(g1));

    Graph g2 = new Graph(6);
    g2.addEdge(1, 4);
    g2.addEdge(4, 5);

    System.out.println(dfs(g2));

  }
}
