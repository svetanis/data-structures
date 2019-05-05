package com.svetanis.datastructures.graph.undirected.traverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.undirected.Graph;

public final class BFS {

  public static ImmutableList<Integer> bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true;

    Queue<Integer> queue = newLinkedList();
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
    print(bfs(g, 4));

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    print(bfs(graph, 2));
  }
}
