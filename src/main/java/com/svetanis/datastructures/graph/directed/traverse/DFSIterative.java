package com.svetanis.datastructures.graph.directed.traverse;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.graph.directed.Graph;

// Depth-first traversal of a directed graph, with a stack instead of recursion.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Returns: nothing; dfs(g) prints one
// line for each fresh start -- the start vertex and everything it newly reaches -- and then
// "components: <number of starts>". For a graph whose every edge goes both ways that number is the
// number of connected components; with one-way edges it is only the number of walks started.
//
// The one idea: a stack holds the vertices met but not yet printed. Pop one, print it, and push
// every vertex it points at that has not been met; the vertex pushed last is popped first, so the
// walk goes on from the newest find. A vertex is marked when it is PUSHED, not when it is popped,
// so it is pushed once. That is why the order differs from the recursive DFS: here the neighbours
// are taken last first, and a vertex is claimed by the first vertex that points at it.
//
// Time: O(V + E) -- each vertex is pushed once and each edge is followed once.
// Space: O(V) -- the marks and the stack.
//
// Siblings: DFS is the recursive version (its order is 0 1 4 3 2 on the same main graph).

public final class DFSIterative {

  public static void dfs(Graph g) {
    int size = g.size();
    int components = 0;
    // a visited array to mark which vertices
    // have been met while doing the walks
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v]) { // START a new walk from a vertex no earlier walk met
        components++;
        explore(g, visited, v);
        System.out.println();
      }
    }
    System.out.printf("components: %d%n", components);
  }

  // explores only what src newly reaches
  public static void explore(Graph g, boolean[] visited, int src) {
    Deque<Integer> stack = new ArrayDeque<>(); // STACK: met, not yet printed
    stack.push(src);
    visited[src] = true; // MARK on push
    while (!stack.isEmpty()) {
      int v = stack.pop();
      System.out.printf("%d ", v); // PRINT in pop order
      for (int u : g.adj(v)) {
        if (!visited[u]) { // SKIP a vertex already met
          visited[u] = true;
          stack.push(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    // two pieces: 0 - 1 - 4 - 3 - 0 with 2 hanging off 0, and the triangle 5 - 6 - 7;
    // every edge is in both directions
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
    // prints:
    // 0 3 4 2 1
    // 5 7 6
    // components: 2
    dfs(g);
  }
}
