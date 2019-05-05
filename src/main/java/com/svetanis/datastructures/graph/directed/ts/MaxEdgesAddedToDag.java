package com.svetanis.datastructures.graph.directed.ts;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.tc;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;
import com.svetanis.java.base.Pair;

public final class MaxEdgesAddedToDag {
  // Time Complexity: O(V + E)

  public static ImmutableList<Pair<Integer, Integer>> edges(Graph g) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    List<Integer> ts = tc(g);
    List<Pair<Integer, Integer>> edges = newArrayList();
    for (int i = 0; i < ts.size(); i++) {
      int v = ts.get(i);
      for (int u : g.adj(v)) {
        visited[u] = true;
      }

      for (int j = i + 1; j < ts.size(); j++) {
        int dst = ts.get(j);
        if (!visited[dst]) {
          edges.add(Pair.build(v, dst));
        }
        visited[dst] = false;
      }
    }
    return newList(edges);
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    print(edges(g));
  }
}
