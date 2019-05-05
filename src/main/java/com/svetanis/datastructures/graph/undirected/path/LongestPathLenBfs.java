package com.svetanis.datastructures.graph.undirected.path;

import static com.google.common.collect.Lists.newLinkedList;
import static java.util.Arrays.fill;

import java.util.Queue;

import com.svetanis.datastructures.graph.undirected.Graph;
import com.svetanis.java.base.Pair;

public final class LongestPathLenBfs {
  // Time Complexity: O(V + E)

  private static final int V = 10;

  public static void longestPathLen(Graph g) {
    Pair<Integer, Integer> p1 = bfs(g, 0);
    Pair<Integer, Integer> p2 = bfs(g, p1.getRight());
    System.out.println("len= " + p2.getLeft());
    System.out.println("from " + p1.getRight() + " to " + p2.getRight());
  }

  private static Pair<Integer, Integer> bfs(Graph g, int src) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    int[] dist = new int[n];
    fill(dist, -1);
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

    int max = 0;
    int index = 0;
    for (int i = 0; i < n; i++) {
      if (dist[i] > max) {
        max = dist[i];
        index = i;
      }
    }
    return Pair.build(max, index);
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(2, 9);
    g.addEdge(2, 4);
    g.addEdge(4, 5);
    g.addEdge(1, 6);
    g.addEdge(6, 7);
    g.addEdge(6, 8);

    longestPathLen(g);
  }

}
