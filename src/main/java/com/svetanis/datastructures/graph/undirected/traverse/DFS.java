package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Depth-first traversal of every component of an undirected graph.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1. No statement fixes the sizes; this is not
// a LeetCode problem and has not been run against a judge. dfs(g) prints each component's
// vertices in depth-first order, one component per line, then "components: " and their number.
// explore(g, v, visited) prints one component's vertices from v; dfs(g, v, visited) does the
// same walk without printing, only marking visited (EulerianPathCircuit uses it for its
// connectivity test).
//
// The one idea: visited[v] says whether the walk has already entered v. Entering a vertex marks
// it and then goes into each neighbour not yet marked, so everything connected to the start is
// printed once before the walk returns. A vertex still unmarked after that walk is in another
// component, so the outer loop starts a new walk from it.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, plus the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. DFSIterative is the same traversal with an
// explicit stack.
//
// Siblings: DFSIterative -- same three methods, explicit stack. AcyclicGraphDfs -- the version
// for graphs without a cycle, which skips the parent instead of keeping visited flags.

public final class DFS {

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
        components++; // NEW COMPONENT: v is in a part not yet reached
        explore(g, v, visited);
        System.out.println();
      }
    }
    System.out.println("components: " + components);
  }

  public static void explore(Graph g, int v, boolean[] visited) {
    // mark current vertex v as visited and print it
    visited[v] = true;
    System.out.print(v + " "); // PRINT: v is entered for the first and only time

    // recursively visit every node connected to
    // vertex v that we have not yet visited
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        explore(g, u, visited); // WALK
      }
    }
  }

  public static void dfs(Graph g, int v, boolean[] visited) {
    // mark the current node as visited and print it
    visited[v] = true; // MARK: v is entered
    // recur for all the vertices adjacent to this vertex
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        dfs(g, u, visited); // WALK
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

    // prints "0 1 4 3 2 ", "5 6 7 " and "components: 2"
    dfs(g);
  }
}
