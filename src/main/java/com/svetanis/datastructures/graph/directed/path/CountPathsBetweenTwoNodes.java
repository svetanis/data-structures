package com.svetanis.datastructures.graph.directed.path;

import com.svetanis.datastructures.graph.directed.Graph;

// Count the paths between two vertices of a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1 (it may contain cycles); src and dst --
// the start and the end. A path may not use any vertex twice. Returns: the number of different
// paths from src to dst (1 when src == dst: the path of one vertex). The count is an int, so it is
// assumed to fit.
//
// The one idea: the number of paths from a vertex to dst is dst's own one path (when the vertex IS
// dst) or the sum, over every edge leaving the vertex to a vertex not already on the path, of the
// number of paths from there. The visited mark is set on the way in and cleared on the way out, so
// it always means "on the path being built"; clearing it is what lets a different path use the
// vertex later and be counted.
//
// Time: exponential in V -- one step for every simple path that starts at src (and every prefix of
// one).
// Space: O(V) -- the marks and the recursion.
//
// Siblings: AllPathsDfs makes this same walk but prints each path; AllPathsBfs prints them
// shortest first.

public final class CountPathsBetweenTwoNodes {

  public static int count(Graph g, int src, int dst) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    return paths(g, src, dst, visited);
  }

  // visited[] marks the vertices on the current path
  private static int paths(Graph g, int src, int dst, boolean[] visited) {
    visited[src] = true; // MARK: src is on the path
    int count = 0;
    if (src == dst) {
      count = 1; // BASE: the path has arrived
    } else {
      for (int v : g.adj(src)) { // EXTEND by every edge leaving src
        if (!visited[v]) { // SKIP a vertex the path already uses
          count += paths(g, v, dst, visited);
        }
      }
    }
    visited[src] = false; // BACKTRACK: src is off the path again
    return count; // RETURN: paths from src to dst that avoid the marked vertices
  }

  public static void main(String[] args) {
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(2, 0);
    g.addEdge(2, 1);
    g.addEdge(1, 3);

    int src = 2;
    int dst = 3;

    System.out.printf("%d-->%d%n", src, dst); // 2-->3
    System.out.println(count(g, src, dst)); // 3 -- 2 0 3, 2 1 3, 2 0 1 3
  }
}
