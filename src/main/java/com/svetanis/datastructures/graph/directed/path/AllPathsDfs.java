package com.svetanis.datastructures.graph.directed.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

public final class AllPathsDfs {

  public static void paths(Graph g, int src, int dst) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    List<Integer> list = newArrayList();
    paths(g, src, dst, visited, list);
  }

  // recursive function to print all paths from 'u' to 'd'
  // visited[] keeps track of vertices in current path
  // list stores actual vertices

  private static void paths(Graph g, int src, int dst, boolean[] visited, List<Integer> list) {
    visited[src] = true;
    list.add(src);
    if (src == dst) {
      print(list);
    } else {
      for (int v : g.adj(src)) {
        if (!visited[v]) {
          paths(g, v, dst, visited, list);
        }
      }
    }
    // remove current vertex from path[]
    // and mark it as unvisited
    list.remove(list.size() - 1);
    visited[src] = false;
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
