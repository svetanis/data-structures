package com.svetanis.datastructures.graph.directed.ts;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalArgument;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

public final class TopologicalSortingKahn {
  // Time Complexity: O(V + E)

  public static ImmutableList<Integer> tc(Graph g) {
    int[] indegree = inDegree(g);
    Queue<Integer> queue = asQueue(indegree);
    List<Integer> list = tc(g, queue, indegree);
    if (list.size() != g.size()) {
      throw illegalArgument("cyclic graph");
    }
    return newList(list);
  }

  private static ImmutableList<Integer> tc(Graph g, Queue<Integer> queue, int[] indegree) {
    List<Integer> list = newArrayList();
    while (!queue.isEmpty()) {
      int u = queue.poll();
      list.add(u);
      for (int v : g.adj(u)) {
        indegree[v]--;
        if (indegree[v] == 0) {
          queue.offer(v);
        }
      }
    }
    return newList(list);
  }

  public static int[] inDegree(Graph g) {
    int size = g.size();
    int[] a = new int[size];
    for (int v = 0; v < size; v++) {
      for (int u : g.adj(v)) {
        a[u]++;
      }
    }
    return a;
  }

  public static Queue<Integer> asQueue(int[] indegree) {
    Queue<Integer> queue = newLinkedList();
    for (int i = 0; i < indegree.length; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }
    return queue;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    print(tc(g));
  }
}
