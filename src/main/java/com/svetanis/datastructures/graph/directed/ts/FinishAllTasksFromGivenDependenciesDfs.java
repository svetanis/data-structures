package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.cycle.CyclicGraphDfs.isCyclic;

import com.svetanis.datastructures.graph.directed.Graph;

public final class FinishAllTasksFromGivenDependenciesDfs {
  // Time Complexity: O(V + E)

  public static boolean isComplete(Graph g) {
    int n = g.size();
    boolean[] path = new boolean[n];
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      if (!visited[i] && isCyclic(g, i, visited, path)) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    System.out.println(isComplete(g));
  }
}
