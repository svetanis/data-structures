package com.svetanis.datastructures.graph.undirected.traverse;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.svetanis.datastructures.graph.undirected.Graph;

// Level of Each node in a Tree from source node (using BFS)

public final class NodeLevelFromSrcBfs {

  public static void print(Graph g, int src) {
    int[] level = bfs(g, src);
    for (int i = 0; i < g.size(); i++) {
      System.out.println(i + "--> " + level[i]);
    }
  }

  private static int[] bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true;

    int[] level = new int[size];
    level[src] = 0;

    Queue<Integer> queue = newLinkedList();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          queue.offer(u);
          visited[u] = true;
          level[u] = level[v] + 1;
        }
      }
    }
    return level;
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(1, 3);
    g.addEdge(3, 1);
    g.addEdge(1, 4);
    g.addEdge(4, 1);
    g.addEdge(1, 5);
    g.addEdge(5, 1);
    g.addEdge(2, 5);
    g.addEdge(5, 2);
    g.addEdge(2, 6);
    g.addEdge(6, 2);
    g.addEdge(6, 7);
    g.addEdge(7, 6);
    print(g, 0);
  }
}
