package com.svetanis.datastructures.graph.undirected.cycle;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class CyclicGraphBfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v] && isCyclic(g, v, visited)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclic(Graph g, int src, boolean[] visited) {

    int n = g.size();
    visited[src] = true;
    int[] parent = new int[n];
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int u = queue.poll();
      for (int v : g.adj(u)) {
        if (!visited[v]) {
          visited[v] = true;
          queue.offer(v);
          parent[v] = u;
        } else if (parent[u] != v) {
          // if an adjacent is visited and
          // not parent of current vertex
          return true; // then there is a cycle
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    System.out.println(isCyclic(g));

    Graph g2 = new Graph(3);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    System.out.println(isCyclic(g2));

  }
}
