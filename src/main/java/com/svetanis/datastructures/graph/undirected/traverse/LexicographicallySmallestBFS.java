package com.svetanis.datastructures.graph.undirected.traverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.undirected.Graph;

public final class LexicographicallySmallestBFS {

  public static ImmutableList<Integer> bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true;

    Queue<Integer> queue = new PriorityQueue<>();
    queue.offer(src);

    List<Integer> list = newArrayList();
    while (!queue.isEmpty()) {
      int v = queue.poll();
      list.add(v);
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Graph graph = new Graph(6);
    graph.addEdge(1, 4);
    graph.addEdge(3, 4);
    graph.addEdge(5, 4);
    graph.addEdge(3, 2);
    graph.addEdge(1, 5);
    print(bfs(graph, 1));
  }
}
