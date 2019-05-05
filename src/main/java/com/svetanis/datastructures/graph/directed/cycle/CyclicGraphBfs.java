package com.svetanis.datastructures.graph.directed.cycle;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

public final class CyclicGraphBfs {

  public static boolean isCyclic(Graph g) {
    // Time Complexity: O(V + E)

    int size = g.size();
    int[] inDegree = inDegree(g);
    Queue<Integer> queue = newLinkedList();
    for (int i = 0; i < size; i++) {
      if (inDegree[i] == 0) {
        queue.offer(i);
      }
    }

    int count = 0;
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int u : g.adj(front)) {
        if (--inDegree[u] == 0) {
          queue.offer(u);
        }
      }
      count++;
    }
    return count != size;
  }

  private static int[] inDegree(Graph g) {
    int size = g.size();
    int[] a = new int[size];
    for (int u = 0; u < size; u++) {
      for (int v : g.adj(u)) {
        a[v]++;
      }
    }
    return a;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    System.out.println(isCyclic(g));
  }
}
