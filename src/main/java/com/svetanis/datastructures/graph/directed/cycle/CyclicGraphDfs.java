package com.svetanis.datastructures.graph.directed.cycle;

import com.svetanis.datastructures.graph.directed.Graph;

// Does a directed graph contain a cycle? (depth-first, by the path being walked)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; repeated edges and self-loops allowed.
// Returns: true if some chain of edges leads from a vertex back to itself (a self-loop counts).
//
// The one idea: each vertex is in one of three states -- not yet reached; on the path the walk is
// on right now (reached, but not all of its outgoing edges are finished); or finished. visited[v]
// says "reached" and stack[v] says "on the current path". An edge from the vertex being walked to
// a vertex that is on the current path leads back to somewhere the walk came from, which is a
// cycle. An edge to a finished vertex is harmless -- that vertex cannot lead back here, or it would
// have found this vertex on its own path. One boolean is not enough to tell those two apart, which
// is why there are two arrays.
//
// Time: O(V + E) -- each vertex is entered once and each edge is looked at once.
// Space: O(V) -- the two arrays and the recursion, which can be V deep.
//
// Siblings: CyclicGraphBfs answers the same question by repeatedly placing vertices nobody points
// at; TopologicalSorting.topologicalSort uses the same three states (as one int array).

public final class CyclicGraphDfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    boolean[] stack = new boolean[size];
    for (int v = 0; v < size; ++v) { // START a walk from every vertex not yet reached
      if (isCyclic(g, v, visited, stack)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isCyclic(Graph g, int v, boolean[] visited, boolean[] stack) {

    if (!visited[v]) {
      visited[v] = true; // MARK reached
      stack[v] = true; // MARK on the current path
      for (int u : g.adj(v)) {
        if (!visited[u] && isCyclic(g, u, visited, stack)) {
          return true;
        } else if (stack[u]) { // BACK EDGE: u is on the current path, so v -> u closes a cycle
          return true;
        }
      }
    }
    stack[v] = false; // LEAVE the path: v is finished
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
    System.out.println(isCyclic(g)); // true -- 0 -> 1 -> 2 -> 0, and 3 -> 3 on its own

    Graph dag = new Graph(3);
    dag.addEdge(0, 1);
    dag.addEdge(1, 2);
    dag.addEdge(0, 2);
    System.out.println(isCyclic(dag)); // false -- 0 -> 2 reaches a finished vertex, not one on the path
  }
}
