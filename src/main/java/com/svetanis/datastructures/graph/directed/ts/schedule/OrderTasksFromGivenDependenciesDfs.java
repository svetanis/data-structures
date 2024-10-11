package com.svetanis.datastructures.graph.directed.ts.schedule;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

public final class OrderTasksFromGivenDependenciesDfs {
  // Time Complexity: O(V + E)

  public static ImmutableList<Integer> ordering(Graph g) {
    int n = g.size();
    boolean[] path = new boolean[n];
    boolean[] visited = new boolean[n];
    List<Integer> list = newArrayList();
    for (int i = 0; i < n; i++) {
      if (!visited[i] && isCyclic(g, i, visited, path, list)) {
        return newList();
      }
    }
    return newList(reverse(list));
  }

  private static boolean isCyclic(Graph g, int v, boolean[] visited, boolean[] path, List<Integer> list) {
    if (!visited[v]) {
      visited[v] = true;
      path[v] = true;
      for (int u : g.adj(v)) {
        if (!visited[u] && isCyclic(g, u, visited, path, list)) {
          return true;
        } else if (path[u]) {
          return true;
        }
      }
    }
    list.add(v);
    path[v] = false; // remove the vertex from recursion stack
    return false;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    print(ordering(g));
  }
}
