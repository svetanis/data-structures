package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Given a graph G and an integer K, K-cores of the graph are connected components 
// that are left after all vertices of degree less than k have been removed

public final class KCores {

  // Time Complexity: O(V + E)

  public static void kCores(Graph g, int k) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    int[] degrees = new int[size];
    for (int v = 0; v < size; v++) {
      degrees[v] = g.adj(v).size();
    }

    // dfs traversal to update degrees
    // of all the vertices

    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        explore(g, v, visited, degrees, k);
      }
    }

    // print K-cores
    for (int v = 0; v < size; v++) {
      if (degrees[v] >= k) {
        System.out.print(v + " ");
        for (int u : g.adj(v)) {
          if (degrees[u] >= k) {
            System.out.print("-> " + u);
          }
        }
        System.out.println();
      }
    }
  }

  private static boolean explore(Graph g, int v, boolean[] visited, int[] degrees, int k) {
    
    visited[v] = true;
    for (int u : g.adj(v)) {
      if (degrees[v] < k) {
        degrees[u]--;
      }
      if (!visited[u]) {
        if (explore(g, u, visited, degrees, k)) {
          degrees[v]--;
        }
      }
    }
    return degrees[v] < k;
  }

  public static void main(String[] args) {
    int k = 3;
    Graph g = new Graph(9);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(1, 5);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(2, 5);
    g.addEdge(2, 6);
    g.addEdge(3, 4);
    g.addEdge(3, 6);
    g.addEdge(3, 7);
    g.addEdge(4, 6);
    g.addEdge(4, 7);
    g.addEdge(5, 6);
    g.addEdge(5, 8);
    g.addEdge(6, 7);
    g.addEdge(6, 8);
    kCores(g, k);
  }
}

