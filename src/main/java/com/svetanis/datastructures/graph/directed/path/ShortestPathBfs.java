package com.svetanis.datastructures.graph.directed.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;
import com.svetanis.java.base.utils.Print;

public final class ShortestPathBfs {
  // Time Complexity: O(V + E)

  private static final int V = 8;

  public static void print(Graph g, int src, int dst) {
    int n = g.size();
    int[] dist = new int[n];
    fill(dist, MAX_VALUE);
    int[] parents = new int[n];
    fill(parents, -1);

    boolean sp = sp(g, src, dst, parents, dist);
    if (!sp) {
      return;
    }

    List<Integer> path = path(src, dst, parents);
    System.out.println("Shortest path len: " + dist[dst]);
    Print.print(path);
  }

  private static boolean sp(Graph g, int src, int dst, int[] parent, int[] dist) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    dist[src] = 0;
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          dist[u] = dist[v] + 1;
          parent[u] = v;
          queue.offer(u);
          if (u == dst) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private static ImmutableList<Integer> path(int src, int dst, int[] parents) {
    List<Integer> path = newArrayList();
    int level = dst;
    path.add(level);
    if (parents[level] != -1) {
      path.add(0, parents[level]);
      level = parents[level];
    }
    path.add(0, src);
    return newList(path);
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(3, 7);
    g.addEdge(4, 5);
    g.addEdge(4, 6);
    g.addEdge(4, 7);
    g.addEdge(5, 6);
    g.addEdge(6, 7);

    int src = 0;
    int dst = 7;
    print(g, src, dst);
  }

}
