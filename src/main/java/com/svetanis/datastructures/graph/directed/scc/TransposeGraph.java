package com.svetanis.datastructures.graph.directed.scc;

import com.svetanis.datastructures.graph.directed.Graph;

// Reverse every edge of a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Returns: a new graph on the same
// vertices that has the edge u -> v for every edge v -> u of g (repeated edges stay repeated).
//
// The one idea: the graph keeps, for each vertex, the list of edges leaving it. Reading every list
// once and writing each edge v -> u into the list of u as u -> v turns the whole graph round. The
// strong-connectivity tests need it because "who can reach 0?" in g is "what does 0 reach?" in the
// reversed graph, and that is a plain walk.
//
// Time: O(V + E) -- every edge is read once and written once.
// Space: O(V + E) -- the new graph.
//
// Siblings: none; StronglyConnected, SccKosarajuBfs and SccKosarajuDfs use it.

public final class TransposeGraph {

  public static Graph transpose(Graph g) {
    Graph reversed = new Graph(g.size());
    for (int v = 0; v < g.size(); ++v) {
      // every edge leaving this vertex
      for (int u : g.adj(v)) {
        reversed.addEdge(u, v); // TURN round: v -> u becomes u -> v
      }
    }
    return reversed;
  }

}
