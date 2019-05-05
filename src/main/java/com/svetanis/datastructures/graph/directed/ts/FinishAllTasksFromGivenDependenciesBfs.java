package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.inDegree;

import com.svetanis.datastructures.graph.directed.Graph;

public final class FinishAllTasksFromGivenDependenciesBfs {
  // Time Complexity: O(V + E)

  public static boolean isComplete(Graph g) {
    int n = g.size();
    int[] indegree = inDegree(g);

    for (int i = 0; i < n; i++) {
      int index = getIndex(indegree);
      if (index == -1) {
        return false;
      }
      indegree[index] = -1;
      for (int u : g.adj(index)) {
        indegree[u]--;
      }
    }
    return true;
  }

  private static int getIndex(int[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++) {
      if (a[i] == 0) {
        return i;
      }
    }
    return -1;
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
