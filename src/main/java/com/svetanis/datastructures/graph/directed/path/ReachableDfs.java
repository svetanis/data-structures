package com.svetanis.datastructures.graph.directed.path;

import com.svetanis.datastructures.graph.directed.Graph;

// Is one vertex reachable from another in a directed graph? (depth-first)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; src and dst -- two vertices. Returns:
// true if some chain of edges leads from src to dst (always true when src == dst).
//
// The one idea: from a vertex, try each edge in turn and ask the same question of its target. A
// mark on every vertex entered is never taken off: once a vertex has been searched from and did
// not lead to dst, searching from it again would find nothing new, so a second visit is skipped.
// The marks also stop a loop in the graph from going on forever. Unlike the path-listing files,
// this search wants one yes or no, not every path, so a mark is never cleared.
//
// Time: O(V + E) -- each vertex is entered once and each edge is looked at once.
// Space: O(V) -- the marks and the recursion, up to V deep.
//
// Siblings: ReachableBfs is the same search with a queue.

public final class ReachableDfs {

  public static boolean isReachable(Graph g, int src, int dst) {
    int size = g.size();
    if (src == dst) { // BASE: a vertex reaches itself
      return true;
    }
    boolean[] visited = new boolean[size];
    return isReachable(g, src, dst, visited);
  }

  private static boolean isReachable(Graph g, int src, int dst, boolean[] visited) {
    visited[src] = true; // MARK on entry, and keep the mark
    if (src == dst) { // FOUND
      return true;
    } else {
      for (int v : g.adj(src)) {
        if (!visited[v]) { // SKIP a vertex already searched from
          if (isReachable(g, v, dst, visited)) {
            return true;
          }
        }
      }
    }
    return false; // RETURN: nothing reachable from src is dst
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
    System.out.println(isReachable(g, 2, 4)); // true -- 2 -> 0 -> 1 -> 4
    System.out.println(isReachable(g, 0, 7)); // false -- 7 is in the other piece

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    System.out.println(isReachable(graph, 1, 3)); // true -- 1 -> 2 -> 3
    System.out.println(isReachable(graph, 3, 1)); // false -- 3 only leads back to 3
  }
}
