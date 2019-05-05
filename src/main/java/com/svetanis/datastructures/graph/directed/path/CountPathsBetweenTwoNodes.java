package com.svetanis.datastructures.graph.directed.path;

import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.svetanis.datastructures.graph.directed.Graph;
import com.svetanis.java.base.utils.IntWrapper;

// Count the total number of ways or paths that exist between two vertices in a directed graph. 

public final class CountPathsBetweenTwoNodes {

  public static int count(Graph g, int src, int dst) {
    int size = g.size();
    IntWrapper count = newIntWrapper();
    boolean[] visited = new boolean[size];
    paths(g, src, dst, count, visited);
    return count.value;
  }

  // recursive function to print all paths from 'u' to 'd'
  // visited[] keeps track of vertices in current path
  // path[] stores actual vertices
  // index is current index in path[]
  private static void paths(Graph g, int src, int dst, IntWrapper count, boolean[] visited) {
    visited[src] = true;
    if (src == dst) {
      count.value++;
    } else {
      for (int v : g.adj(src)) {
        if (!visited[v]) {
          paths(g, v, dst, count, visited);
        }
      }
    }
    // remove current vertex from path[]
    // and mark it as unvisited
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
    System.out.println(count(g, src, dst));
  }
}
