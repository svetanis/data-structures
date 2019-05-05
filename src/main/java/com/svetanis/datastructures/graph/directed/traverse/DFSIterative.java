package com.svetanis.datastructures.graph.directed.traverse;

import java.util.Stack;

import com.svetanis.datastructures.graph.directed.Graph;

public final class DFSIterative {

  // Time Complexity: O(V + E)

  public static void dfs(Graph g) {
    int size = g.size();
    int components = 0;
    // a visited array to mark which vertices
    // have been visited while doing the DFS
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v]) {
        components++;
        explore(g, visited, v);
        System.out.println();
      }
    }
    System.out.println("components: " + components);
  }

  // explores only one connected componentCount
  public static void explore(Graph g, boolean[] visited, int src) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(src);
    visited[src] = true;
    while (!stack.isEmpty()) {
      int v = stack.pop();
      System.out.print(v + " ");
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          stack.push(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 0);
    g.addEdge(1, 4);
    g.addEdge(4, 1);
    g.addEdge(3, 4);
    g.addEdge(4, 3);
    g.addEdge(5, 6);
    g.addEdge(6, 5);
    g.addEdge(5, 7);
    g.addEdge(7, 5);
    g.addEdge(6, 7);
    g.addEdge(7, 6);
    dfs(g);
  }
}
