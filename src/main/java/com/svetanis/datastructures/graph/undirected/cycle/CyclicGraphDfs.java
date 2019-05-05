package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class CyclicGraphDfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v] && isCyclic(g, v, visited, -1)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclic(Graph g, int v, boolean[] visited, int parent) {

    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        if (isCyclic(g, u, visited, v)) {
          return true;
        }
      } else if (u != parent) {
        // if an adjacent is visited and
        // not parent of current vertex
        return true; // then there is a cycle
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    System.out.println(isCyclic(g));

    Graph g2 = new Graph(3);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    System.out.println(isCyclic(g2));
  }
}
