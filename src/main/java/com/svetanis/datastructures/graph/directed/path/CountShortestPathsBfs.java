package com.svetanis.datastructures.graph.directed.path;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;

import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// dist[] represents the shortest distances from source vertex
// paths[] represents the number of different shortest paths from the source vertex to each of the vertices

public final class CountShortestPathsBfs {

  public static void paths(Graph g, int src) {
    int n = g.size();
    int[] dist = new int[n];
    int[] paths = new int[n];
    fill(dist, MAX_VALUE);
    paths(g, src, dist, paths);
    print(paths);
  }

  private static void paths(Graph g, int src, int[] dist, int[] paths) {
    int n = g.size();
    dist[src] = 0;
    paths[src] = 1;
    boolean[] visited = new boolean[n];
    visited[src] = true;
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int x : g.adj(front)) {
        if (!visited[x]) {
          queue.offer(x);
          visited[x] = true;
        }
        if (dist[x] > dist[front] + 1) {
          dist[x] = dist[front] + 1;
          paths[x] = paths[front];
        } else if (dist[x] == dist[front] + 1) {
          paths[x] += paths[front];
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(3, 5);
    g.addEdge(4, 6);
    g.addEdge(5, 6);
    int src = 0;
    paths(g, src);
  }
}
