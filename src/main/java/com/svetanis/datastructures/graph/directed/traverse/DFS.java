package com.svetanis.datastructures.graph.directed.traverse;

import com.svetanis.datastructures.graph.directed.Graph;

// Depth-first traversal of a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Three entry points: dfs(g) sweeps the
// whole graph and prints; explore(g, v, visited) walks from v and prints; dfs(g, v, visited) walks
// from v silently. visited[] marks the vertices already met and is shared by successive walks.
//
// dfs(g) prints one line for each fresh start -- the start vertex and everything it newly reaches,
// in walk order -- and then "components: <number of starts>". For a graph whose every edge goes
// both ways that number is the number of connected components; in a graph with one-way edges it is
// only the number of walks started, since a later start may lead into vertices already met.
//
// The one idea: from a vertex, mark it, then go on to each vertex it points at that is not marked,
// and do the same there; the walk goes as deep as it can along one edge before it tries the next.
// The mark on entry makes sure a vertex is entered once, and stops a loop from going on forever.
//
// Time: O(V + E) -- each vertex is entered once and each edge is followed once.
// Space: O(V) -- the marks and the recursion, up to V deep.
//
// Siblings: DFSIterative is the same sweep with a stack instead of recursion (and a different walk
// order); BFS and BFSDisconnected are the breadth-first versions.

public final class DFS {

  public static void dfs(Graph g) {
    int size = g.size();
    int components = 0;
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) { // START a new walk from a vertex no earlier walk met
        components++;
        explore(g, v, visited);
        System.out.println();
      }
    }
    System.out.printf("components: %d%n", components);
  }

  // explores only what v newly reaches
  public static void explore(Graph g, int v, boolean[] visited) {
    visited[v] = true; // MARK on entry
    System.out.printf("%d ", v); // PRINT in walk order
    for (int u : g.adj(v)) {
      if (!visited[u]) { // SKIP a vertex already met
        explore(g, u, visited);
      }
    }
  }

  public static void dfs(Graph g, int v, boolean[] visited) {
    visited[v] = true; // MARK on entry
    for (int u : g.adj(v)) {
      if (!visited[u]) { // SKIP a vertex already met
        dfs(g, u, visited);
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
    // 0 1 4 3 2
    // 5 6 7
    // components: 2
    dfs(g);
  }
}
