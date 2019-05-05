package com.svetanis.datastructures.graph.undirected.path;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class ShortestPathLenBfs {
  // Time Complexity: O(V + E)

  private static final int V = 9;

  public static int sp(Graph g, int src, int dst) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    int[] dist = new int[n];
    dist[src] = 0;

    Queue<Integer> queue = newLinkedList();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          dist[u] = dist[v] + 1;
          queue.offer(u);
        }
      }
    }
    return dist[dst];
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(0, 7);
    g.addEdge(1, 7);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(2, 5);
    g.addEdge(2, 8);
    g.addEdge(3, 4);
    g.addEdge(3, 5);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 7);
    g.addEdge(7, 8);

    int src = 0;
    int dst = 5;
    System.out.println(sp(g, src, dst));
  }

}
