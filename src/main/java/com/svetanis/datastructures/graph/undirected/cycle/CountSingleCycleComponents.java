package com.svetanis.datastructures.graph.undirected.cycle;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.collect.Lists.filter;

import java.util.List;

import com.svetanis.datastructures.graph.undirected.Graph;

// Given a set of ‘n’ vertices and ‘m’ edges of an undirected simple graph (no parallel edges and no self-loop), 
// find the number of single-cycle-components present in the graph. 
// A single-cyclic-component is a graph of n nodes containing a single cycle through all nodes of the component.
// A single-cycle-component is a connected component where every vertex has the degree as two.

public final class CountSingleCycleComponents {
  // Time Complexity: O(V + E)

  public static int count(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int count = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        List<Integer> list = newArrayList();
        dfs(g, v, visited, list);
        if (degreeTwo(g, list)) {
          count++;
        }
      }
    }
    return count;
  }

  private static boolean degreeTwo(Graph g, List<Integer> list) {
    List<Integer> transformed = transform(list, v -> g.degree(v));
    List<Integer> filtered = filter(transformed, v -> v == 2);
    return filtered.size() == list.size();
  }

  private static void dfs(Graph g, int v, boolean[] visited, List<Integer> list) {
    list.add(v);
    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        dfs(g, u, visited, list);
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(16);
    g.addEdge(1, 10);
    g.addEdge(1, 5);
    g.addEdge(5, 10);
    g.addEdge(2, 9);
    g.addEdge(9, 15);
    g.addEdge(2, 15);
    g.addEdge(2, 12);
    g.addEdge(12, 15);
    g.addEdge(13, 8);
    g.addEdge(6, 14);
    g.addEdge(14, 3);
    g.addEdge(3, 7);
    g.addEdge(7, 11);
    g.addEdge(11, 6);

    System.out.println(count(g));

  }
}
