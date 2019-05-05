package com.svetanis.datastructures.graph.directed.ts;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.asQueue;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.inDegree;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

public final class OrderTasksFromGivenDependenciesBfs {
  // Time Complexity: O(V + E)

  public static ImmutableList<Integer> ordering(Graph g) {
    int[] indegree = inDegree(g);
    Queue<Integer> queue = asQueue(indegree);
    return tc(g, queue, indegree);
  }

  private static ImmutableList<Integer> tc(Graph g, Queue<Integer> queue, int[] indegree) {
    int n = g.size();
    List<Integer> list = newArrayList();
    for (int i = 0; i < n; i++) {
      if (queue.isEmpty()) {
        return newList();
      }
      int u = queue.poll();
      list.add(u);
      for (int v : g.adj(u)) {
        if (--indegree[v] == 0) {
          queue.offer(v);
        }
      }
    }
    return newList(list);
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
