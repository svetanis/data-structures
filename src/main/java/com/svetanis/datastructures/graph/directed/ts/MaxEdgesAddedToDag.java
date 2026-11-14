package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.tc;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

// Maximum number of edges that can be added to a directed acyclic graph so that it stays acyclic.
//
// Input: g -- a directed acyclic graph on the vertices 0 .. n - 1, with no repeated edge. Returns:
// the edges to add, each as a pair {from, to}: one for every pair of vertices that has no edge
// between them yet, pointing the way the topological order points. Adding them all leaves a DAG
// with n * (n - 1) / 2 edges in all, which is the most any DAG on n vertices can have, so the answer
// has n * (n - 1) / 2 - E pairs.
//
// The one idea: put the vertices in a topological line, so that every edge points forward. Any pair
// of vertices can be joined by one more edge pointing forward along the line without creating a
// cycle, because a cycle needs some edge to point backward. So for each vertex, every LATER vertex
// in the line that it does not already point at is a pair to add. No DAG can hold more than one
// edge per pair of vertices (two edges the opposite way round would be a cycle), so this is the
// maximum.
//
// Time: O(V^2 + E) -- the loop over later vertices is V^2 / 2 steps; building the order is O(V + E).
// Space: O(V + answer) -- the order, the marks and the list of pairs.
//
// Siblings: none; it uses TopologicalSortingKahn.tc for the line.

public final class MaxEdgesAddedToDag {

  public static List<int[]> edges(Graph g) {
    int n = g.size();
    boolean[] visited = new boolean[n]; // VISITED here means: v already points at this vertex
    List<Integer> ts = tc(g);
    List<int[]> edges = new ArrayList<>();
    for (int i = 0; i < ts.size(); i++) {
      int v = ts.get(i);
      for (int u : g.adj(v)) {
        visited[u] = true; // MARK the vertices v already points at
      }

      for (int j = i + 1; j < ts.size(); j++) { // WALK the vertices later in the line
        int dst = ts.get(j);
        if (!visited[dst]) { // ADD: v -> dst joins two vertices that had no edge
          edges.add(new int[] {v, dst});
        }
        visited[dst] = false; // CLEAR the mark for the next v
      }
    }
    return edges;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    // every pair the topological order leaves unjoined: 15 pairs on 6
    // vertices, minus the 6 edges already here
    for (int[] edge : edges(g)) {
      System.out.printf("%d->%d ", edge[0], edge[1]);
    }
    System.out.println(); // 4->5 4->2 4->3 5->3 5->1 2->0 2->1 0->3 0->1 -- 9 pairs
  }
}
