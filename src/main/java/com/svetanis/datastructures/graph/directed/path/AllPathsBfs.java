package com.svetanis.datastructures.graph.directed.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

public final class AllPathsBfs {

  public static void paths(Graph g, int src, int dst) {
    List<Integer> path = newArrayList();
    path.add(src);
    Queue<List<Integer>> queue = newLinkedList();
    queue.offer(path);
    while (!queue.isEmpty()) {
      path = queue.poll();
      int last = path.get(path.size() - 1);
      if (last == dst) {
        print(path);
      }
      List<Integer> adj = g.adj(last);
      for (int i = 0; i < adj.size(); i++) {
        int next = adj.get(i);
        if (isNotVisited(path, next)) {
          List<Integer> list = newArrayList(path);
          list.add(next);
          queue.offer(list);
        }
      }
    }
  }

  private static boolean isNotVisited(List<Integer> path, int x) {
    return !isVisited(path, x);
  }

  private static boolean isVisited(List<Integer> path, int x) {
    int n = path.size();
    for (int i = 0; i < n; i++) {
      if (path.get(i) == x) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(2, 0);
    g.addEdge(2, 1);
    g.addEdge(1, 3);

    int src = 2;
    int dst = 3;

    System.out.println(src + "-->" + dst);
    paths(g, src, dst);
  }
}
