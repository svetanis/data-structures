package com.svetanis.datastructures.graph.directed.path;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Given a Directed Graph and two vertices in it, check whether there is a path from the first given vertex to second. 

public final class ReachableBfs {

  public static boolean isReachable(Graph g, int src, int dst) {
    int size = g.size();
    if (src == dst) {
      return true;
    }
    boolean[] visited = new boolean[size];
    visited[src] = true;
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int u : g.adj(front)) {
        if (u == dst) {
          return true;
        }
        // else continue to do BFS
        if (!visited[u]) {
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 0);
    g.addEdge(1, 4);
    g.addEdge(4, 1);
    g.addEdge(3, 4);
    g.addEdge(4, 3);
    g.addEdge(5, 6);
    g.addEdge(6, 5);
    g.addEdge(5, 7);
    g.addEdge(7, 5);
    g.addEdge(6, 7);
    g.addEdge(7, 6);
    System.out.println(isReachable(g, 2, 4));
    System.out.println(isReachable(g, 0, 7));

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    System.out.println(isReachable(graph, 1, 3));
    System.out.println(isReachable(graph, 3, 1));
  }
}
