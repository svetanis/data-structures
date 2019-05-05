package com.svetanis.datastructures.graph.undirected.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.graph.undirected.Graph;

public final class ReachableNodes {

  public static void reachable(Graph g, int[] a) {
    int n = g.size();
    int scc = 0;
    int[] visited = new int[n + 1];
    Multimap<Integer, Integer> mm = ArrayListMultimap.create();
    for (int u : a) {
      if (visited[u] == 0) {
        scc++;
        List<Integer> list = bfs(g, u, scc, visited);
        mm.putAll(visited[u], list);
      }
      System.out.print(u + "--> ");
      System.out.println(mm.get(visited[u]));
    }
  }

  private static ImmutableList<Integer> bfs(Graph g, int src, int scc, int[] visited) {
    visited[src] = scc;
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);
    List<Integer> list = newArrayList();
    while (!queue.isEmpty()) {
      int v = queue.poll();
      list.add(v);
      for (int u : g.adj(v)) {
        if (visited[u] == 0) {
          visited[u] = scc;
          queue.offer(u);
        }
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a = { 2, 4, 5 };
    Graph g = new Graph(8);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(3, 1);
    g.addEdge(5, 6);
    g.addEdge(5, 7);
    reachable(g, a);
  }
}
