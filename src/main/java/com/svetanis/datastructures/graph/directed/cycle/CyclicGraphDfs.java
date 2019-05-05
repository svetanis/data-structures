package com.svetanis.datastructures.graph.directed.cycle;

import com.svetanis.datastructures.graph.directed.Graph;

public final class CyclicGraphDfs {

  public static boolean isCyclic(Graph g) {
    // Time Complexity: O(V + E)

    int size = g.size();
    boolean[] visited = new boolean[size];
    boolean[] stack = new boolean[size];
    for (int v = 0; v < size; ++v) {
      if (isCyclic(g, v, visited, stack)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isCyclic(Graph g, int v, boolean[] visited, boolean[] stack) {

    if (!visited[v]) {
      visited[v] = true;
      stack[v] = true;
      for (int u : g.adj(v)) {
        if (!visited[u] && isCyclic(g, u, visited, stack)) {
          return true;
        } else if (stack[u]) {
          return true;
        }
      }
    }
    stack[v] = false; // remove the vertex from recursion stack
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(2, 3);
    g.addEdge(3, 3);
    System.out.println(isCyclic(g));
  }
}
