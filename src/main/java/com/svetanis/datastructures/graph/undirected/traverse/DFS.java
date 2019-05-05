package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class DFS {
  // Time Complexity: O(V + E)

  public static void dfs(Graph g) {
    int size = g.size();
    // the number of components in the graph
    int components = 0;
    // a visited array to mark which vertices
    // have been visited while doing the DFS
    boolean[] visited = new boolean[size];

    // call the recursive helper function
    // to print dfs traversal
    // do the dfs for each vertex not yet visited
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        components++;
        explore(g, v, visited);
        System.out.println();
      }
    }
    System.out.println("components: " + components);
  }

  public static void explore(Graph g, int v, boolean[] visited) {
    // mark current vertex v as visited and print it
    visited[v] = true;
    System.out.print(v + " ");

    // recursively visit every node connected to
    // vertex v that we have not yet visited
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        explore(g, u, visited);
      }
    }
  }

  public static void dfs(Graph g, int v, boolean[] visited) {
    // mark the current node as visited and print it
    visited[v] = true;
    // recur for all the vertices adjacent to this vertex
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        dfs(g, u, visited);
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(1, 4);
    g.addEdge(3, 4);
    g.addEdge(5, 6);
    g.addEdge(5, 7);
    g.addEdge(6, 7);

    dfs(g);
  }
}
