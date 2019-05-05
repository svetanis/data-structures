package com.svetanis.datastructures.graph.directed.scc;

import com.svetanis.datastructures.graph.directed.Graph;

public final class TransposeGraph {

  public static Graph transpose(Graph g) {
    Graph reversed = new Graph(g.size());
    for (int v = 0; v < g.size(); ++v) {
      // recur for all the vertices
      // adjacent to this vertex
      for (int u : g.adj(v)) {
        reversed.addEdge(u, v);
      }
    }
    return reversed;
  }

}
