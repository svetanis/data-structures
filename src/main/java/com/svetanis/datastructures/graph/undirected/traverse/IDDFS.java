package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Iterative deepening depth-first search (IDDFS): is dst within d edges of src?
//
// Input: a Graph g, a source vertex src, a destination vertex dst and a depth limit d >= 0. No
// statement fixes the sizes; this is not a LeetCode problem and has not been run against a
// judge. Returns true when some path of at most d edges leads from src to dst, which is the same
// as "the breadth-first distance from src to dst is at most d". A negative d returns false even
// when src == dst.
//
// The one idea: a depth-first search takes little memory but may dive down a long wrong branch,
// while a breadth-first search finds the nearest vertices first but queues a whole ring. IDDFS
// takes the best of both: run a depth-limited depth-first search with limit 0, then 1, then 2,
// and so on up to d. The first limit that finds dst is the fewest edges needed, and each run
// only ever holds the one path it is on, at most limit vertices deep. Nothing marks a vertex as
// visited, so a run may walk back over its own steps; the depth limit is what stops it.
//
// Time: O(b^d) -- with b the largest degree: a run with limit i can try every walk of i edges,
// and since vertices are not marked a walk may go back and forth, so the work is not bounded by
// V + E. On the complete graph on 6 vertices, every vertex has 5 neighbours and each extra limit
// multiplies the calls by about 5.
// Space: O(d) -- the recursion, at most d + 1 calls deep.
//
// Siblings: BFS -- visits the same vertices ring by ring, in O(V + E) time and O(V) space.

public final class IDDFS {

  public static boolean iddfs(Graph g, int src, int dst, int d) {
    for (int i = 0; i <= d; i++) {
      if (dls(g, src, dst, i)) { // DEEPEN: one more edge allowed than the last run
        return true;
      }
    }
    return false;
  }

  // depth-limited search: is dst reachable from src in at most d edges?
  private static boolean dls(Graph g, int src, int dst, int d) {
    if (src == dst) {
      return true; // BASE: found it
    }
    if (d <= 0) {
      return false; // BASE: out of edges
    }

    for (int u : g.adj(src)) {
      if (dls(g, u, dst, d - 1)) { // WALK: one edge used
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 5);
    g.addEdge(2, 6);

    System.out.println(iddfs(g, 0, 6, 3)); // true, 0-2-6 is 2 edges
    System.out.println(iddfs(g, 0, 6, 1)); // false, 6 is 2 edges away
  }
}
